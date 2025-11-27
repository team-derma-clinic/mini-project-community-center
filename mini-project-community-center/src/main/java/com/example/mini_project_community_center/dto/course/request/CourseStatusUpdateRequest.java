package com.example.mini_project_community_center.dto.course.request;

import com.example.mini_project_community_center.common.enums.course.CourseStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CourseStatusUpdateRequest(
        @NotNull(message = "status는 비워질 수 없습니다.")
        @Size(max = 20, message = "status는 20자 이내여야합니다.")
        CourseStatus status
) {}
