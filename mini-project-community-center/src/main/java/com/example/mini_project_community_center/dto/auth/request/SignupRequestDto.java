package com.example.mini_project_community_center.dto.auth.request;

import com.example.mini_project_community_center.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequestDto (
        @NotBlank(message = "아이디는 필수입니다.")
        @Size(min = 4, max = 50, message = "아이디는 4~50자 사이여야 합니다.")
        String loginId,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 100, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        String password,

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

) {
    /**
     * DTO → Entity 변환 (비밀번호 암호화 전 상태)
     * 암호화는 Service에서 PasswordEncoder로 처리
     */
    // to) DTO -> Entity 변환할 떄
    //     : DTO를 Entity로 만든다
    public User toEntity(String encodedPassword) {
        return User.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .name(name)
                .email(email)
                .phone(phone)
                .build();
    }
}