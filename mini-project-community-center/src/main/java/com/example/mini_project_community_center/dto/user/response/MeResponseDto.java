package com.example.mini_project_community_center.dto.user.response;

import com.example.mini_project_community_center.common.enums.user.RoleType;
import com.example.mini_project_community_center.entity.user.User;

public record MeResponseDto(
        Long id,
        String loginId,
        String name,
        String email,
        RoleType role,
        String provider
) {
    public static MeResponseDto from(User user) {
        return new MeResponseDto(
                user.getId(),
                user.getLoginId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getProvider().name()
        );
    }
}
