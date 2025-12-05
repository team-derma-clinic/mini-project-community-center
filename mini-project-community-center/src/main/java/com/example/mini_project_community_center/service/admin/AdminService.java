package com.example.mini_project_community_center.service.admin;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.role.RoleRequestDto;
import com.example.mini_project_community_center.dto.user.response.UserListItemResponse;

public interface AdminService {

    ResponseDto<UserListItemResponse> updateRole(Long userId, RoleRequestDto dto);
}
