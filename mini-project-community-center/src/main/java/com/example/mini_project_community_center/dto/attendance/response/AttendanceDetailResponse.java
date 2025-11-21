package com.example.mini_project_community_center.dto.attendance.response;

import com.example.mini_project_community_center.common.enums.AttendanceStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AttendanceDetailResponse (
        Long id,
        Long sessionId,
        Long userId,
        String userName,
        AttendanceStatus status,
        String note,
        String markedAt
){
}
