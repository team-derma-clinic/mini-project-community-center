package com.example.mini_project_community_center.dto.enrollment.response;

import com.example.mini_project_community_center.common.enums.EnrollmentsStatus;
import com.example.mini_project_community_center.entity.Enrollment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EnrollmentListItemResponse(
        Long id,
        Long courseId,
        String courseName,
        EnrollmentsStatus status,
        LocalDateTime enrolledAt
) {
    public static EnrollmentListItemResponse fromListEntity(Enrollment enrollment) {
        return new EnrollmentListItemResponse(
                enrollment.getId(),
                enrollment.getCourse().getId(),
                enrollment.getCourse().getTitle(),
                enrollment.getStatus(),
                enrollment.getEnrolledAt()
        );
    }
}
