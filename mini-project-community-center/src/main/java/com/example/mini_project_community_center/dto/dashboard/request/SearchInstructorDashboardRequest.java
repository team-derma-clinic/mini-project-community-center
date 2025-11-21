package com.example.mini_project_community_center.dto.dashboard.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchInstructorDashboardRequest(
        String from,
        String to
) {
}
