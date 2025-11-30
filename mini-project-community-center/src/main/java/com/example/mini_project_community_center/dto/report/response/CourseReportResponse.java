package com.example.mini_project_community_center.dto.report.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CourseReportResponse(
        Long courseId,
        String courseName,
        Integer capacity,
        Integer refundCount,
        Double enrollmentRate
) {
}
