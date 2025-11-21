package com.example.mini_project_community_center.dto.center.response;

import com.example.mini_project_community_center.dto.course.response.CourseListItemResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CenterDetailResponse(
        Long id,
        String name,
        String address,
        Double latitude,
        Double longitude,
        String phone,
        String createdAt,
        List<CourseListItemResponse> courses
) {
//    public static CenterDetailResponse of() {
//
//    }
}
