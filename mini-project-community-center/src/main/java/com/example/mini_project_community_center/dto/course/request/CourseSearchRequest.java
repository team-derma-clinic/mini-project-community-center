package com.example.mini_project_community_center.dto.course.request;

import com.example.mini_project_community_center.common.enums.course.CourseCategory;
import com.example.mini_project_community_center.common.enums.course.CourseLevel;
import com.example.mini_project_community_center.common.enums.course.CourseStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CourseSearchRequest(
        @Positive(message = "centerId는 양수여야합니다.")
        @NotNull(message = "centerId는 필수입니다.")
        Long centerId,

        CourseCategory category,

        CourseLevel level,

        CourseStatus status,

        String from,

        String to,

        @Min(value = 1, message = "weekday는 1~7범위여야 합니다.")
        @Max(value = 7, message = "weekday는 1~7범위여야 합니다.")
        Integer weekday,

        String timeRange,

        @Max(value = 50, message = "검색어는 50자이하여야 합니다.")
        String q
) {}
