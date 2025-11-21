package com.example.mini_project_community_center.dto.report.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CourseReportResponse(
        List<CourseReportItemResponse> courses
) {
}
