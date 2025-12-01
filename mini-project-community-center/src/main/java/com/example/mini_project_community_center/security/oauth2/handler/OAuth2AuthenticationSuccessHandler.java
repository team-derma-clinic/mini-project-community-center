package com.example.mini_project_community_center.security.oauth2.handler;

import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.entity.auth.RefreshToken;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.security.provider.JwtProvider;
import com.example.mini_project_community_center.repository.auth.RefreshTokenRepository;
import com.example.mini_project_community_center.repository.user.UserRepository;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.security.util.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.oauth2.authorized-redirect-uri}")
    private String redirectUrl;

    private static final String REFRESH_TOKEN = "refreshToken";

    @Override
    public void onAuthenticationSuccess(
                                         HttpServletRequest request,
                                         HttpServletResponse response,
                                         Authentication authentication
    ) throws IOException, ServletException {

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        String loginId = principal.getLoginId();

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        String role = principal.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_STUDENT");

        // JWT 토큰 생성 (Access/Refresh)
        String accessToken = jwtProvider.generateAccessToken(loginId, role);
        String refreshToken = jwtProvider.generateRefreshToken(loginId, role);

        // 방금 생성한 refreshToken의 남은 만료 시간을 밀리초 단위로 환산
        long refreshMillis = jwtProvider.getRemainingMillis(refreshToken);

        // 현재 시각 + 남은 밀리초를 더해 만료 시각을 계산
        Instant refreshExpiry = Instant.now().plusMillis(refreshMillis);

        // RefreshToken 엔티티를 DB에 upsert(있으면 갱신, 없으면 새로 저장)
        refreshTokenRepository.findByUser(user)
                .ifPresentOrElse(
                        rt -> rt.renew(refreshToken, refreshExpiry),
                        () -> refreshTokenRepository.save(
                                RefreshToken.builder()
                                        .user(user)             // 어떤 유저의 토큰인지
                                        .token(refreshToken)    // 실제 리프레시 토큰 문자열
                                        .expiry(refreshExpiry)  // 만료 시각 전달
                                        .build()
                        ));

        CookieUtils.addHttpOnlyCookie(
                response,
                REFRESH_TOKEN,
                refreshToken,
                (int) (refreshMillis / 1000),
                false // HTTPS 에서만 전송하도록 Secure 옵션 설정 - 원래는 true
        );

        // 프론트엔드로 보낼 리다이렉터 URL 생성
        // +) accessToken은 쿼리 파라미터에 포함하여 전송
        String targetUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("accessToken", accessToken)
                .build()
                .toUriString();

        // 세션에 남아 있을 수 있는 인증 관련 속성들 정리
        clearAuthenticationAttributes(request);

        // 최종적으로 클라이언트를 targetUrl로 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
