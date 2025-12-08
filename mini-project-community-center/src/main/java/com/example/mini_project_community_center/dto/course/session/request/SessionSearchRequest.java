package com.example.mini_project_community_center.dto.course.session.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SessionSearchRequest(
        String from,

        String to,

        @Min(value = 1, message = "weekday는 1~7범위여야 합니다.")
        @Max(value = 7, message = "weekday는 1~7범위여야 합니다.")
        Integer weekday,

        String timeRange,

        @Size(max = 50, message = "검색어는 50자이하여야 합니다.")
        String q
) {}
