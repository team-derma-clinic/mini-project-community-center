package com.example.mini_project_community_center.dto.center.response;

import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.common.utils.ValueMapper;
import com.example.mini_project_community_center.entity.center.Center;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record CenterDetailResponse(
        Long id,
        String name,
        String address,
        Double latitude,
        Double longitude,
        String phone,
        String createdAt
) {
    public static CenterDetailResponse fromEntity(Center center) {
        return new CenterDetailResponse(
                center.getId(),
                center.getName(),
                center.getAddress(),
                center.getLatitude() != null ? ValueMapper.toDoubleOrNull(center.getLatitude()) : null,
                center.getLongitude() != null ? ValueMapper.toDoubleOrNull(center.getLongitude()) : null,
                center.getPhone(),
                DateUtils.toKstString(center.getCreatedAt())
        );
    }
}
