package com.example.mini_project_community_center.dto.attendance.request;

import com.example.mini_project_community_center.common.enums.AttendanceStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AttendanceUpdateRequest(
        AttendanceStatus status,

        @Size(max = 255, message = "note 입력은 최대 255자까지 가능합니다.")
        String note
) {
}
