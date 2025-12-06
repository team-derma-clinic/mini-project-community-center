package com.example.mini_project_community_center.service.admin.impl;

import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.common.enums.user.RoleStatus;
import com.example.mini_project_community_center.common.enums.user.RoleType;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.user.response.UserDetailResponseDto;
import com.example.mini_project_community_center.dto.user.response.UserListItemResponseDto;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.repository.user.UserRepository;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<Void> approve(UserPrincipal principal, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (user.getRoleStatus().equals(RoleStatus.APPROVED)) {
            throw new IllegalArgumentException("이미 승인된 사용자입니다: " + userId);
        }

        user.changeRoleStatus(RoleStatus.APPROVED);

        return ResponseDto.success("승인되었습니다.");
    }

    @Transactional
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<Void> reject(UserPrincipal principal, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (user.getRoleStatus().equals(RoleStatus.APPROVED)) {
            throw new IllegalArgumentException("이미 승인된 사용자입니다: " + userId);
        }

        if (user.getRoleStatus().equals(RoleStatus.REJECTED)) {
            throw new IllegalArgumentException("이미 거절된 사용자입니다: " + userId);
        }

        user.changeRoleStatus(RoleStatus.REJECTED);

        return ResponseDto.success("거절되었습니다.");
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<List<UserDetailResponseDto>> getPendingUsers() {
        List<UserDetailResponseDto> data = null;

        List<User> pending = userRepository.findAllByRoleStatus(RoleStatus.PENDING);

        data = pending.stream()
                .map(UserDetailResponseDto::from)
                .toList();

        return ResponseDto.success(data);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<List<UserListItemResponseDto>> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserListItemResponseDto> data = users.stream()
                .map(UserListItemResponseDto::from)
                .toList();

        return ResponseDto.success(data);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<List<UserListItemResponseDto>> getUsersByRole(RoleType role) {
        List<User> users = userRepository.findUsersByRole(role);

        List<UserListItemResponseDto> data = users.stream()
                .map(UserListItemResponseDto::from)
                .toList();
        return ResponseDto.success(data);
    }
}
