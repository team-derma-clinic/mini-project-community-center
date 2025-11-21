package com.example.mini_project_community_center.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequestDto(
        @NotBlank(message = "이름은 필수입니다.")
        @Size(max = 50, message = "이름은 최대 50자까지 가능합니다.")
        String name,

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @Size(max = 30)
        @Pattern(
                regexp = "^(010)-?([0-9]{3,4})-?([0-9]{4})",
                message = "휴대폰 형식이 올바르지 않습니다."
        )
        String phone
) {}
