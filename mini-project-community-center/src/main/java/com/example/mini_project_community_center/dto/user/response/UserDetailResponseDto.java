package com.example.mini_project_community_center.dto.user.response;

import com.example.mini_project_community_center.common.enums.RoleType;

import java.time.LocalDateTime;

public record UserDetailResponseDto(
        Long id,
        String name,
        String loginId,
        String email,
        String phone,
        RoleType role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
