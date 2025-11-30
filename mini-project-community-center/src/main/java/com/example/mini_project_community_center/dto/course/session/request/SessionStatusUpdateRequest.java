package com.example.mini_project_community_center.dto.course.session.request;

import com.example.mini_project_community_center.common.enums.course.CourseSessionsStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SessionStatusUpdateRequest(
        @NotNull(message = "status는 비워질 수 없습니다.")
        CourseSessionsStatus status
) {}
