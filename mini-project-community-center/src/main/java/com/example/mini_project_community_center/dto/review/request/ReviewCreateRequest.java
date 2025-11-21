package com.example.mini_project_community_center.dto.review.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ReviewCreateRequest(
@NotNull(message = "courseId는 필수입니다.")
Long courseId,

@NotNull(message = "rating은 필수입니다.")
@Min(value = 1, message = "rating은 1이상이어야 합니다.")
@Max(value = 5, message = "rating은 5이하여야 합니다.")
Byte rating,

String content
) {
}
