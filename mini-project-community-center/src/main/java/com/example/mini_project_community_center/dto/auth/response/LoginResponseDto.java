package com.example.mini_project_community_center.dto.auth.response;

import com.example.mini_project_community_center.common.enums.user.RoleType;

public record LoginResponseDto (
        String accessToken,
        long accessTokenExpiresInMillis,
        RoleType role
) {
    // of) 일반적인 정적 팩토리 생성, 값 그대로 매핑 (여러 값을 조합해 생성)
    public static LoginResponseDto of(
            String accessToken,
            long accessTokenExpiresInMillis,
            RoleType role
    ) {
        return new LoginResponseDto(accessToken, accessTokenExpiresInMillis, role);
    }
}
