package com.example.mini_project_community_center.dto.enrollment.response;

import com.example.mini_project_community_center.common.enums.enrollment.EnrollmentsStatus;
import com.example.mini_project_community_center.entity.enrollment.Enrollment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EnrollmentDetailResponse(
        Long id,
        Long userId,
        Long courseId,
        String courseName,
        EnrollmentsStatus status,
        LocalDateTime enrolledAt,
        String cancelReason
) {
    public static EnrollmentDetailResponse fromDetailEntity(Enrollment enrollment) {
        return new EnrollmentDetailResponse(
                enrollment.getId(),
                enrollment.getUser().getId(),
                enrollment.getCourse().getId(),
                enrollment.getCourse().getTitle(),
                enrollment.getStatus(),
                enrollment.getEnrolledAt(),
                enrollment.getCancelReason()
        );
    }
}
