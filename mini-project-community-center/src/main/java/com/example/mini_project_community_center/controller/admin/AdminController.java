package com.example.mini_project_community_center.controller.role;

import com.example.mini_project_community_center.common.apis.RoleApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.role.RoleRequestDto;
import com.example.mini_project_community_center.dto.user.response.UserListItemResponse;
import com.example.mini_project_community_center.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RoleApi.ROOT)
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PutMapping(RoleApi.ROLE)
    public ResponseEntity<ResponseDto<UserListItemResponse>> updateRoles(
            @PathVariable Long userId,
            @RequestBody RoleRequestDto dto
    ){
        ResponseDto<UserListItemResponse> data = roleService.updateRole(userId, dto);
        return ResponseEntity.status(data.getStatus()).body(data);
    }
}
