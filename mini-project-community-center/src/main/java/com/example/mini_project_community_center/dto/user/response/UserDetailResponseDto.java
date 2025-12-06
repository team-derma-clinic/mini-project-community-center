package com.example.mini_project_community_center.dto.user.response;

import com.example.mini_project_community_center.common.enums.user.RoleStatus;
import com.example.mini_project_community_center.common.enums.user.RoleType;
import com.example.mini_project_community_center.entity.user.User;

import java.time.LocalDateTime;

public record UserDetailResponseDto(
        Long id,
        String name,
        String loginId,
        String email,
        String phone,
        RoleType role,
        RoleStatus roleStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UserDetailResponseDto from(User user) {
        return new  UserDetailResponseDto(
                user.getId(),
                user.getName(),
                user.getLoginId(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getRoleStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );

    }
}
