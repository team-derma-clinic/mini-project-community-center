package com.example.mini_project_community_center.dto.system.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SystemHealthResponse(
        String status,
        String timestamp,
        String version,
        DatabaseHealthResponse database
) {
}
