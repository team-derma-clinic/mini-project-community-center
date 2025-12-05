package com.example.mini_project_community_center.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordChangeRequestDto(
        @NotBlank(message = "새 비밀번호는 필수입니다.")
        @Size(max = 50, message = "비밀번호는 최대 50자까지 가능합니다.")
        String oldPassword,

        @NotBlank(message = "비밀번호 확인은 필수입니다.")
        @Size(max = 50, message = "비밀번호는 최대 50자까지 가능합니다.")
        String newPassword,

        @NotBlank(message = "비밀번호 확인은 필수입니다.")
        @Size(max = 50, message = "비밀번호는 최대 50자까지 가능합니다.")
        String confirmPassword
) {}
