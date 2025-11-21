package com.example.mini_project_community_center.dto.dashboard.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchStaffDashboardRequest(
        Long centerId,
        String from,
        String to
) {
}
