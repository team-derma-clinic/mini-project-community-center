package com.example.mini_project_community_center.dto.review.response;

import com.example.mini_project_community_center.common.enums.review.ReviewStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ReviewListItemResponse(
        Long id,
        Long courseId,
        String courseName,
        Long userId,
        String userName,
        Byte rating,
        String content,
        ReviewStatus status,
        String createdAt
) {
}
