package com.example.mini_project_community_center.controller.system;

import com.example.mini_project_community_center.common.apis.CenterApi;
import com.example.mini_project_community_center.common.apis.SystemApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.system.response.SystemHealthResponse;
import com.example.mini_project_community_center.dto.system.response.SystemInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SystemController {
    private final SystemService systemService;

    // GET /api/v1/health
    @GetMapping(SystemApi.HEALTH)
    public ResponseEntity<ResponseDto<SystemHealthResponse>> getSystemHealth() {
        ResponseDto<SystemHealthResponse> data = systemService.getSystemHealth();
        return ResponseEntity.ok(data);
    }

    // GET /api/v1/info
    @GetMapping(SystemApi.INFO)
    public ResponseEntity<ResponseDto<SystemInfoResponse>> getSystemInfo() {
        ResponseDto<SystemInfoResponse> data = systemService.getSystemInfo();
        return ResponseEntity.ok(data);
    }
}
