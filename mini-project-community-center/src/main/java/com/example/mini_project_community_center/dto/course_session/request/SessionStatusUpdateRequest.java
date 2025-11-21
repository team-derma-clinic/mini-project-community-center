package com.example.mini_project_community_center.dto.course_session.request;

import com.example.mini_project_community_center.common.enums.CourseSessionsStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SessionStatusUpdateRequest(
        @NotNull(message = "status는 비워질 수 없습니다.")
        @Size(max = 20, message = "status는 20자 이내여야 합니다.")
        CourseSessionsStatus status
) {}
