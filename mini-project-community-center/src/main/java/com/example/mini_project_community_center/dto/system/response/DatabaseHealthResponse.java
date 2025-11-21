package com.example.mini_project_community_center.dto.system.response;

public record DatabaseHealthResponse(
        String status,
        Long responseTime
) {
}
