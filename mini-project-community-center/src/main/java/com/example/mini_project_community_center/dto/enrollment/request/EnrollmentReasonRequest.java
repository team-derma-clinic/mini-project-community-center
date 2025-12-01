package com.example.mini_project_community_center.dto.enrollment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EnrollmentReasonRequest(
   @NotNull(message = "cancelReason은 비워질 수 없습니다.")
   @Size(max = 200, message = "cancelReason 입력은 최대 200자까지 가능합니다.")
   String cancelReason
) {}
