package com.example.mini_project_community_center.service.admin;

import com.example.mini_project_community_center.common.enums.user.RoleType;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.user.response.UserDetailResponseDto;
import com.example.mini_project_community_center.dto.user.response.UserListItemResponseDto;
import com.example.mini_project_community_center.security.user.UserPrincipal;

import java.util.List;

public interface AdminService {

    ResponseDto<Void> approve(UserPrincipal principal, Long userId);

    ResponseDto<Void> reject(UserPrincipal principal, Long userId);

    ResponseDto<List<UserDetailResponseDto>> getPendingUsers();

    ResponseDto<List<UserListItemResponseDto>> getAllUsers();

    ResponseDto<List<UserListItemResponseDto>> getUsersByRole(RoleType role);
}
