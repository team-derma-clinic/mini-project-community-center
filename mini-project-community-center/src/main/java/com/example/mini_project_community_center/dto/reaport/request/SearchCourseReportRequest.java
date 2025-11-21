package com.example.mini_project_community_center.dto.reaport.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchCourseReportRequest(
        Long centerId,
        String from,
        String to,
        String sort,
        Integer limit
) {
}
