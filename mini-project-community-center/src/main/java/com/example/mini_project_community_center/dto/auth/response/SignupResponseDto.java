package com.example.mini_project_community_center.dto.auth.response;


import com.example.mini_project_community_center.entity.user.User;

public record SignupResponseDto (
        Long userId,
        String loginId,
        String name,
        String email
) {
    // from) Entity -> DTO 변환할 때
    //       : Entity로부터 DTO를 만든다
    public static SignupResponseDto from(User user) {
        return new SignupResponseDto(
                user.getId(),
                user.getLoginId(),
                user.getName(),
                user.getEmail()
        );
    }
}
