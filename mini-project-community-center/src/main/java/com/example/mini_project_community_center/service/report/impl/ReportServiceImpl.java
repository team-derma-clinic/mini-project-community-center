package com.example.mini_project_community_center.service.report.impl;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.report.request.CourseReportRequest;
import com.example.mini_project_community_center.dto.report.response.CourseReportResponse;
import com.example.mini_project_community_center.repository.report.ReportRepository;
import com.example.mini_project_community_center.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    @Override
    public ResponseDto<List<CourseReportResponse>> getCourseReport(CourseReportRequest req) {
        List<CourseReportResponse> data = reportRepository.getCourseReport(req);
        return ResponseDto.success(data);
    }
}
