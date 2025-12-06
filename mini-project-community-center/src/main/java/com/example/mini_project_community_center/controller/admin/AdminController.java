package com.example.mini_project_community_center.controller.admin;

import com.example.mini_project_community_center.common.apis.AdminApi;
import com.example.mini_project_community_center.common.enums.user.RoleType;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.user.response.UserDetailResponseDto;
import com.example.mini_project_community_center.dto.user.response.UserListItemResponseDto;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.admin.AdminService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AdminApi.ROOT)
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PutMapping(AdminApi.APPROVE)
    public ResponseEntity<ResponseDto<Void>> approve(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("userId") @Positive(message = "사용자 ID는 1 이상의 정수여야합니다") Long userId
    ){
        ResponseDto<Void> data = adminService.approve(principal, userId);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @PutMapping(AdminApi.REJECT)
    public ResponseEntity<ResponseDto<Void>> reject(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("userId") @Positive(message = "사용자 ID는 1 이상의 정수여야합니다") Long userId
    ){
        ResponseDto<Void> data = adminService.reject(principal, userId);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping(AdminApi.PENDING)
    public ResponseEntity<ResponseDto<List<UserDetailResponseDto>>> getPendingUsers() {
        ResponseDto<List<UserDetailResponseDto>> data = adminService.getPendingUsers();
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<UserListItemResponseDto>>> getAllUsers()
    {
        ResponseDto<List<UserListItemResponseDto>> data = adminService.getAllUsers();
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping(AdminApi.ROLE)
    public ResponseEntity<ResponseDto<List<UserListItemResponseDto>>> getUsersByRole(
            @RequestParam RoleType role
    ){
        ResponseDto<List<UserListItemResponseDto>> data = adminService.getUsersByRole(role);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

}
