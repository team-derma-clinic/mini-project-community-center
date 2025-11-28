package com.example.mini_project_community_center.dto.course_session.request;

import com.example.mini_project_community_center.common.enums.course.CourseSessionsStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SessionCreateRequest(
        @NotBlank(message = "startTime은 비워질 수 없습니다.")
        @Size(max = 30, message = "startTime은 30자 이내여야 합니다.")
        String startTime,

        @NotBlank(message = "endTime은 비워질 수 없습니다.")
        @Size(max = 30, message = "endTime 30자 이내여야 합니다.")
        String endTime,

        @Size(max = 60, message = "room은 60자 이내여야 합니다.")
        String room,

        @NotNull(message = "status는 비워질 수 없습니다.")
        CourseSessionsStatus status
) {}
