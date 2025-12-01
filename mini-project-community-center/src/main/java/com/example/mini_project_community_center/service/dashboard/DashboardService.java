package com.example.mini_project_community_center.service.dashboard;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.dashboard.request.SearchInstructorDashboardRequest;
import com.example.mini_project_community_center.dto.dashboard.request.SearchStaffDashboardRequest;
import com.example.mini_project_community_center.dto.dashboard.response.InstructorDashboardResponse;
import com.example.mini_project_community_center.dto.dashboard.response.StaffDashboardResponse;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import jakarta.validation.Valid;

public interface DashboardService {
    ResponseDto<InstructorDashboardResponse> getInstructorDashboard(UserPrincipal userPrincipal, @Valid SearchInstructorDashboardRequest req);
    ResponseDto<StaffDashboardResponse> getStaffDashboard(UserPrincipal userPrincipal, @Valid SearchStaffDashboardRequest req);
}
