package com.example.mini_project_community_center.dto.reaport.response;

import com.example.mini_project_community_center.common.enums.CourseCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CourseReportItemResponse(
    Long courseId,
    String courseName,
    Long centerId,
    String centerName,
    CourseCategory category,
    Integer enrolledCount,
    Integer capacity,
    Double enrollmentRate,
    Integer refundedCount,
    BigDecimal refundAmount
) {
}
