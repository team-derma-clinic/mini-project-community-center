package com.example.mini_project_community_center.service.auth.impl;

import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.common.enums.user.RoleStatus;
import com.example.mini_project_community_center.common.enums.user.RoleType;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.auth.request.LoginRequestDto;
import com.example.mini_project_community_center.dto.auth.request.PasswordResetRequestDto;
import com.example.mini_project_community_center.dto.auth.request.SignupRequestDto;
import com.example.mini_project_community_center.dto.auth.response.LoginResponseDto;
import com.example.mini_project_community_center.dto.auth.response.PasswordVerifyResponseDto;
import com.example.mini_project_community_center.dto.auth.response.SignupResponseDto;
import com.example.mini_project_community_center.entity.auth.RefreshToken;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.repository.auth.RefreshTokenRepository;
import com.example.mini_project_community_center.repository.user.UserRepository;
import com.example.mini_project_community_center.security.provider.JwtProvider;
import com.example.mini_project_community_center.security.user.UserPrincipalMapper;
import com.example.mini_project_community_center.security.util.CookieUtils;
import com.example.mini_project_community_center.service.auth.AuthService;
import com.example.mini_project_community_center.service.auth.EmailService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final UserPrincipalMapper userPrincipalMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${app.oauth2.authorized-redirect-uri}")
    private String redirectUri;

    private static final String REFRESH_TOKEN = "refresh_token";

    @Override
    @Transactional
    public ResponseDto<SignupResponseDto> register(
            SignupRequestDto request,
            RoleType roleType,
            RoleStatus roleStatus
    ) {
        if (userRepository.findByLoginId(request.loginId()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_USER);
        }

        if (!request.password().equals(request.confirmPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_CONFIRM_MISMATCH);
        }

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        if (roleType == null || roleStatus == null) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }

        System.out.println("provider name:" + request.provider().name());

        User newUser = User.builder()
                .loginId(request.loginId())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .name(request.name())
                .provider(request.provider())
                .role(roleType)
                .roleStatus(roleStatus)
                .build();

        userRepository.save(newUser);

        boolean isStaffOrInstructor =
                roleType == RoleType.INSTRUCTOR || roleType == RoleType.STAFF;

        if (isStaffOrInstructor) {
            return ResponseDto.success("회원가입 완료, 관리자의 승인이 필요합니다.", SignupResponseDto.from(newUser));
        }

        return ResponseDto.success("회원가입 완료", SignupResponseDto.from(newUser));

    }

    @Override
    @Transactional
    public ResponseDto<LoginResponseDto> login(LoginRequestDto request, HttpServletResponse response) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(
                    request.loginId(), request.password()
            );

            var authentication = authenticationManager.authenticate(authToken);

            String loginId = authentication.getName();

            var principal = userPrincipalMapper.toPrincipal(loginId);

            String rawRole = principal.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElseThrow();

            String roleName = rawRole.replace("ROLE_", "");
            RoleType role = RoleType.valueOf(roleName);

            String accessToken = jwtProvider.generateAccessToken(loginId, role.name());
            String refreshToken = jwtProvider.generateRefreshToken(loginId, role.name());

            long accessExpiresIn = jwtProvider.getRemainingMillis(accessToken);
            long refreshRemaining = jwtProvider.getRemainingMillis(refreshToken);

            Instant refreshExpiry = Instant.now().plusMillis(refreshRemaining);

            User user = userRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

            if (user.getRoleStatus() != RoleStatus.APPROVED) {
                throw new BusinessException(ErrorCode.USER_NOT_APPROVED);
            }

            refreshTokenRepository.findByUser(user)
                    .ifPresentOrElse(
                            r -> { r.renew(refreshToken, refreshExpiry);
                        refreshTokenRepository.save(r);
                    },
                            () -> {
                                RefreshToken r = RefreshToken.builder()
                                        .user(user)
                                        .token(refreshToken)
                                        .expiry(refreshExpiry)
                                        .build();
                                refreshTokenRepository.save(r);
                            }
                    );

            CookieUtils.addHttpOnlyCookie(
                    response,
                    REFRESH_TOKEN,
                    refreshToken,
                    (int) (refreshRemaining / 1000),
                    true
            );

            return ResponseDto.success(
                    "로그인 되었습니다.", LoginResponseDto.of(accessToken, accessExpiresIn, role)
            );

        } catch (BadCredentialsException ex) {
            throw new BusinessException(ErrorCode.AUTHENTICATION_FAILED);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseDto<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.getCookie(request, REFRESH_TOKEN).ifPresent(cookie -> {
            String refreshToken = cookie.getValue();

            if (jwtProvider.isValidToken(refreshToken)) {
                String loginId = jwtProvider.getLoginIdFromJwt(refreshToken);
                userRepository.findByLoginId(loginId).ifPresent(user -> refreshTokenRepository.deleteByUser(user));
            }
        });

        CookieUtils.deleteCookie(response, REFRESH_TOKEN);

        return ResponseDto.success("로그아웃 되었습니다.");
    }

    @Override
    @Transactional
    public ResponseDto<LoginResponseDto> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtils.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElseThrow(() -> new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if (!jwtProvider.isValidToken(refreshToken)) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }

        String loginId = jwtProvider.getLoginIdFromJwt(refreshToken);

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        RefreshToken stored = refreshTokenRepository.findByUser(user)
                .orElseThrow(() -> new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if (!stored.getToken().equals(refreshToken)) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID, "리프레시 토큰이 일치하지 않습니다.");
        }

        if (stored.isExpired()) {
            throw new  BusinessException(ErrorCode.TOKEN_EXPIRED);
        }

        var principal = userPrincipalMapper.toPrincipal(loginId);
        String rawRole = principal.getAuthorities()
                .stream()
                .findFirst()
                .map(a -> a.getAuthority())
                .orElseThrow();

        String roleName = rawRole.replace("ROLE_", "");
        RoleType role = RoleType.valueOf(roleName);

        String newAccessToken = jwtProvider.generateAccessToken(loginId, role.name());
        String newRefreshToken = jwtProvider.generateRefreshToken(loginId, role.name());

        long accessExpiresIn = jwtProvider.getRemainingMillis(newAccessToken);
        long refreshRemaining = jwtProvider.getRemainingMillis(newRefreshToken);

        stored.renew(newRefreshToken, Instant.now().plusMillis(refreshRemaining));
        refreshTokenRepository.save(stored);

        CookieUtils.addHttpOnlyCookie(
                response,
                REFRESH_TOKEN,
                newRefreshToken,
                (int) (refreshRemaining) / 1000,
                false
        );

        return ResponseDto.success(
                "토큰 재발급 완료", LoginResponseDto.of(newAccessToken, accessExpiresIn, role)
        );
    }

    @Override
    @Transactional
    public ResponseDto<Void> sendPasswordResetEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        String token = jwtProvider.generateEmailJwtToken(email, "RESET_PASSWORD");

        String url = "https://myservice.com/reset-password?token=" + token;

        emailService.sendPasswordReset(email, url);

        return ResponseDto.success("비밀번호 재설정 이메일 전송 완료되었습니다.");
    }

    @Override
    @Transactional
    public ResponseDto<PasswordVerifyResponseDto> verifyPasswordToken(String token) {

        if (!jwtProvider.isValidToken(token)) {
            return ResponseDto.success(PasswordVerifyResponseDto.failure());
        }

        String email = jwtProvider.getEmailFromEmailToken(token);
        return ResponseDto.success(PasswordVerifyResponseDto.success(email));
    }

    @Override
    @Transactional
    public ResponseDto<Void> resetPassword(PasswordResetRequestDto request) {

        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_CONFIRM_MISMATCH);
        }

        String token = request.token();

        if (!jwtProvider.isValidToken(token)) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }

        String email = jwtProvider.getEmailFromEmailToken(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.changePassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        refreshTokenRepository.findByUser(user)
                .ifPresent(refreshTokenRepository::delete);

        return ResponseDto.success("비밀번호 재설정이 완료되었습니다.");
    }

}
