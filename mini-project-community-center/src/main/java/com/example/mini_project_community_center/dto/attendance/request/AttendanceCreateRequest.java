package com.example.mini_project_community_center.dto.attendance.request;

import com.example.mini_project_community_center.common.enums.attendance.AttendanceStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AttendanceCreateRequest (
    @NotNull(message = "sessionId는 필수입니다.")
    Long sessionId,

    @NotNull(message = "userId는 필수입니다.")
    Long userId,

    @NotNull(message = "status는 필수입니다.")
    AttendanceStatus status
){}
