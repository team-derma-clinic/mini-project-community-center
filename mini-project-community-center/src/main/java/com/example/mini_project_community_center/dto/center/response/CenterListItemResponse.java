package com.example.mini_project_community_center.dto.center.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CenterListItemResponse(
        Long id,
        String name,
        String address
) {}
