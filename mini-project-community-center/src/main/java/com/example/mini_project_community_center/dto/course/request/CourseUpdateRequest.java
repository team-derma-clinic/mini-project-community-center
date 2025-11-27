package com.example.mini_project_community_center.dto.course.request;

import com.example.mini_project_community_center.common.enums.course.CourseCategory;
import com.example.mini_project_community_center.common.enums.course.CourseLevel;
import com.example.mini_project_community_center.common.enums.course.CourseStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CourseUpdateRequest(
        @NotNull(message = "centerId는 필수입니다.")
        @Positive(message = "centerId는 양수여야합니다.")
        Long centerId,

        @Size(max = 200)
        String title,

        @Size(max = 50)
        CourseCategory category,

        @Size(max = 20)
        CourseLevel level,

        @Max(value = 30, message = "capacity는 최대 30명입니다.")
        @Positive(message = "capacity는 양수여야합니다.")
        Integer capacity,

        @Positive(message = "fee는 양수여야합니다.")
        BigDecimal fee,

        @Size(max = 20, message = "status는 20자 이내여야합니다.")
        CourseStatus status,

        @Size(max = 1000, message = "description은 1000자 이내여야 합니다.")
        String description,

        List<Long> instructorIds,

        String startDate,

        String endDate
) {}
