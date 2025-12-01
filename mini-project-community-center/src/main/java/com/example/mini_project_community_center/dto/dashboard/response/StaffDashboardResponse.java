package com.example.mini_project_community_center.dto.dashboard.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StaffDashboardResponse(
        Integer totalCenters,
        Integer totalCourses,
        Integer totalEnrollments,
        Integer totalRefunds,
        ReportSummary reportSummary
) {
}
