package com.example.mini_project_community_center.service.report;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.report.request.CourseReportRequest;
import com.example.mini_project_community_center.dto.report.response.CourseReportResponse;

import java.util.List;

public interface ReportService {
    ResponseDto<List<CourseReportResponse>> getCourseReport(CourseReportRequest req);
}
