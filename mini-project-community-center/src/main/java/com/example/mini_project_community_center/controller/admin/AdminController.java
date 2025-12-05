package com.example.mini_project_community_center.controller.admin;

import com.example.mini_project_community_center.common.apis.AdminApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.role.RoleRequestDto;
import com.example.mini_project_community_center.dto.user.response.UserListItemResponse;
import com.example.mini_project_community_center.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminApi.ROOT)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService roleService;

    @PutMapping(AdminApi.ROLE)
    public ResponseEntity<ResponseDto<UserListItemResponse>> updateRoles(
            @PathVariable Long userId,
            @RequestBody RoleRequestDto dto
    ){
        ResponseDto<UserListItemResponse> data = roleService.updateRole(userId, dto);
        return ResponseEntity.status(data.getStatus()).body(data);
    }
}
