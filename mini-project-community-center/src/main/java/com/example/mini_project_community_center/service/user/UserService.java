package com.example.mini_project_community_center.service.user;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.role.RoleRequestDto;
import com.example.mini_project_community_center.dto.user.request.UserUpdateRequestDto;
import com.example.mini_project_community_center.dto.user.response.UserDetailResponseDto;
import com.example.mini_project_community_center.dto.user.response.UserListItemResponse;
import com.example.mini_project_community_center.security.user.UserPrincipal;

public interface UserService {
    ResponseDto<UserDetailResponseDto> getMe(Long id);

    ResponseDto<UserDetailResponseDto> getUserById(Long userId);

    ResponseDto<UserListItemResponse> updateUserInfo(UserPrincipal userPrincipal, UserUpdateRequestDto dto);

    ResponseDto<UserListItemResponse> updateRole(Long userId, RoleRequestDto dto);
}
