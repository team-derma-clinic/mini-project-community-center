package com.example.mini_project_community_center.controller;

import com.example.mini_project_community_center.common.apis.ReportApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.report.request.CourseReportRequest;
import com.example.mini_project_community_center.dto.report.response.CourseReportResponse;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ReportApi.ROOT)
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    // 1. 강좌별 통계(등록/정원/환불)
    // GET /api/v1/reports/courses
    @GetMapping(ReportApi.COURSES)
    public ResponseEntity<ResponseDto<CourseReportResponse>> getCourseReport(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(required = false) Long centerId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer limit
    ) {
        CourseReportRequest req = new CourseReportRequest(centerId, from, to, sort, limit);
        ResponseDto<CourseReportResponse> data = reportService.getCourseReport(req);
        return ResponseEntity.ok(data);
    }

    // 2. 출석 통계(세션/강좌/사용자)

    // 3. 카테고리별 통계(등록수/평균펼점/인기순위)

    // 4. 강사별 통계(담당강좌수/평균평점/출석률?

}
