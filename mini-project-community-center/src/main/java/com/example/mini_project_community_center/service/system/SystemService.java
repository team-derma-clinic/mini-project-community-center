package com.example.mini_project_community_center.service.system;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.system.response.SystemHealthResponse;
import com.example.mini_project_community_center.dto.system.response.SystemInfoResponse;

public interface SystemService {
    ResponseDto<SystemHealthResponse> getSystemHealth();
    ResponseDto<SystemInfoResponse> getSystemInfo();
}
