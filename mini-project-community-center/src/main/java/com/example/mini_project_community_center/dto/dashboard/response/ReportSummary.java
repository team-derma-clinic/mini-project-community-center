package com.example.mini_project_community_center.dto.dashboard.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ReportSummary(CourseReportSummary courseReport) {
    public record CourseReportSummary(
            Double averageEnrollmentRate,
            TopCourse topCourse
    ) {}

    public record TopCourse(
            String title,
            Double enrollmentRate,
            Double attendanceRate
    ) {}
}
