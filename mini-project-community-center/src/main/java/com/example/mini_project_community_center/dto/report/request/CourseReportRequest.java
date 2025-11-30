package com.example.mini_project_community_center.dto.report.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CourseReportRequest(
        Long centerId,
        String from,
        String to,
        String sort,
        Integer limit
) {
}
