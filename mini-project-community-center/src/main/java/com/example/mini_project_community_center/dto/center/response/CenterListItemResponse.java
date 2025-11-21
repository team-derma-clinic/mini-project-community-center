package com.example.mini_project_community_center.dto.center.response;

import com.example.mini_project_community_center.entity.Center;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CenterListItemResponse(
        Long id,
        String name,
        String address
) {
    public static CenterListItemResponse fromEntity(Center center) {
        return new CenterListItemResponse(
                center.getId(),
                center.getName(),
                center.getAddress()
        );
    }

    public static List<CenterListItemResponse> from(List<Center> centers) {
        return centers.stream()
                .map(CenterListItemResponse::fromEntity)
                .toList();
    }
}
