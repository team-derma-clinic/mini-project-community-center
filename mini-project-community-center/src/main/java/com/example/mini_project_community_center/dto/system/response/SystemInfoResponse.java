package com.example.mini_project_community_center.dto.system.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SystemInfoResponse (
String version,
String name,
String description,
String environment,
String buildTime,
Long uptime
) {
}
