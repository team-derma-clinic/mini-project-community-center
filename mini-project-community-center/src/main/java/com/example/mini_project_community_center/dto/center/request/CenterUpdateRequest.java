package com.example.mini_project_community_center.dto.center.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CenterUpdateRequest(
        @NotBlank(message = "name 은 비워질 수 없습니다.")
        @Size(max = 120, message = "name 입력은 최대 120자까지 가능합니다.")
        String name,

        @Size(max = 255, message = "address 입력은 최대 255자까지 가능합니다.")
        String address,

        @Pattern(
                regexp = "^-?([1-8]?[0-9](\\.\\d{1,7})?|90(\\.0{1,7})?)$",
                message = "latitude는 -90 ~ 90 사이의 소수점 최대 7자리까지 허용가능합니다."
        )
        String latitude,

        @Pattern(
                regexp = "^-?(180(\\.0{1,7})?|((1[0-7][0-9])|([1-9]?[0-9]))(\\.\\d{1,7})?)$",
                message = "longitude는 -180 ~ 180 사이의 소수점 최대 7자리까지 허용가능합니다."
        )
        String longitude,

        @Pattern(
                regexp = "^(\\+82-?)?010-?\\d{3,4}-?\\d{4}$",
                message = "전화번호 형식이 올바르지 않습니다."
        )
        String phone
) {
}
