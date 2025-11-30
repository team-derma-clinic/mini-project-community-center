package com.example.mini_project_community_center.controller.dashboard;

import com.example.mini_project_community_center.common.apis.DashboardApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.dashboard.request.SearchInstructorDashboardRequest;
import com.example.mini_project_community_center.dto.dashboard.response.InstructorDashboardResponse;
import com.example.mini_project_community_center.dto.dashboard.response.StaffDashboardResponse;
import com.example.mini_project_community_center.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    // GET /api/v1/instructors/me/dashboard
    @GetMapping(DashboardApi.INSTRUCTOR_ME)
    public ResponseEntity<ResponseDto<InstructorDashboardResponse>> getInstructorDashboard(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid SearchInstructorDashboardRequest req
    ) {
        ResponseDto<InstructorDashboardResponse> data = dashboardService.getInstructorDashboard(userPrincipal, req);
        return ResponseEntity.ok(data);
    }

    // GET /api/v1/staff/dashboard
    @GetMapping(DashboardApi.STAFF)
    public ResponseEntity<ResponseDto<StaffDashboardResponse>> getStaffDashboard(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid SearchInstructorDashboardRequest req
    ) {
        ResponseDto<StaffDashboardResponse> data = dashboardService.getStaffDashboard(userPrincipal, req);
        return ResponseEntity.ok(data);
    }
}
