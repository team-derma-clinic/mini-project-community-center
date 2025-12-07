package com.example.mini_project_community_center.dto.course.request;

import com.example.mini_project_community_center.common.enums.course.CourseCategory;
import com.example.mini_project_community_center.common.enums.course.CourseLevel;
import com.example.mini_project_community_center.common.enums.course.CourseStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CourseCreateRequest(
        @NotNull(message = "centerId는 필수입니다.")
        @Positive(message = "centerId는 양수여야합니다.")
        Long centerId,

        @NotBlank(message = "title은 필수입니다.")
        @Size(max = 200)
        String title,

        @NotNull(message = "category는 필수입니다.")
        CourseCategory category,

        @NotNull(message = "level은 필수입니다.")
        CourseLevel level,

        @NotNull(message = "capacity는 필수입니다.")
        @Max(value = 30, message = "capacity는 최대 30명입니다.")
        @Positive(message = "capacity는 양수여야합니다.")
        Integer capacity,

        @NotNull(message = "fee는 필수입니다.")
        @Positive(message = "fee는 양수여야합니다.")
        BigDecimal fee,

        @NotNull(message = "status는 필수입니다.")
        CourseStatus status,

        @NotBlank(message = "description은 필수입니다.")
        @Size(max = 1000, message = "description은 1000자 이내여야 합니다.")
        String description,

        @NotEmpty(message = "instructor 리스트는 비어있을 수 없습니다.")
        List<Long> instructorIds,

        @NotBlank(message = "startDate는 필수입니다.")
        String startDate,

        @NotBlank(message = "endDate는 필수입니다.")
        String endDate
) {}
