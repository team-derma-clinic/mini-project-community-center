package com.example.mini_project_community_center.dto.dashboard.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InstructorDashboardResponse(
        Integer totalCourses,
        Integer totalSessions,
        Integer totalAttendance,
        Double attendanceRate
) {
}
