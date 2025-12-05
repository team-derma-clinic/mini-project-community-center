package com.example.mini_project_community_center.service.user.impl;

import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.user.request.PasswordChangeRequestDto;
import com.example.mini_project_community_center.dto.user.request.UserUpdateRequestDto;
import com.example.mini_project_community_center.dto.user.response.MeResponseDto;
import com.example.mini_project_community_center.dto.user.response.UserDetailResponseDto;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.repository.user.UserRepository;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public ResponseDto<MeResponseDto> getMe(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        MeResponseDto dto = MeResponseDto.from(user);

        return ResponseDto.success(dto);
    }

    @Override
    public ResponseDto<UserDetailResponseDto> getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        UserDetailResponseDto dto = UserDetailResponseDto.from(user);

        return ResponseDto.success(dto);
    }

    @Override
    @Transactional
    public ResponseDto<Void> updatePassword(UserPrincipal userPrincipal, PasswordChangeRequestDto dto) {
        Long userId = userPrincipal.getId();
        if (userId == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!dto.newPassword().equals(dto.confirmPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_CONFIRM_MISMATCH);
        }

        if (dto.oldPassword() != null) {
            if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
                throw new BusinessException(ErrorCode.INVALID_INPUT);
            }
        }

        String encodedPassword = passwordEncoder.encode(dto.newPassword());
        user.changePassword(encodedPassword);

        return ResponseDto.success("비밀번호가 변경되었습니다.");
    }

    @Override
    @Transactional
    public ResponseDto<MeResponseDto> updateUserInfo(UserPrincipal userPrincipal, UserUpdateRequestDto dto) {
        Long userId = userPrincipal.getId();
        if (userId == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        String newName = (dto.name() == null || dto.name().isBlank()) ? user.getName() : dto.name();
        String newEmail = (dto.email() == null || dto.email().isBlank()) ? user.getEmail() : dto.email();
        String newPhone = (dto.phone() == null || dto.phone().isBlank()) ? user.getPhone() : dto.phone();

        user.updateUserInfo(newName, newEmail, newPhone);

        MeResponseDto data = MeResponseDto.from(user);

        return ResponseDto.success("회원 정보가 수정되었습니다.", data);
    }
}
