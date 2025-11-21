package com.example.mini_project_community_center.dto.enrollment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@JsonIgnoreProperties(ignoreUnknown = true)
public record EnrollmentCreateRequest(
        @NotBlank(message = "courseId는 비워질 수 없습니다.")
        Long courseId,

        @NotBlank(message = "method는 비워질 수 없습니다.")
        @Size(max = 20, message = "method 입력은 최대 20자까지 가능합니다.")
        String method
) {}
