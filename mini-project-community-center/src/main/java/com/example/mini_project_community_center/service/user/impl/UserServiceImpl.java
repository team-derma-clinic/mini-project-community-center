package com.example.mini_project_community_center.service.user.impl;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.role.RoleRequestDto;
import com.example.mini_project_community_center.dto.user.request.UserUpdateRequestDto;
import com.example.mini_project_community_center.dto.user.response.UserDetailResponseDto;
import com.example.mini_project_community_center.dto.user.response.UserListItemResponse;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public ResponseDto<UserDetailResponseDto> getMe(Long id) {
        return null;
    }

    @Override
    public ResponseDto<UserDetailResponseDto> getUserById(Long userId) {
        return null;
    }

    @Override
    public ResponseDto<UserListItemResponse> updateUserInfo(UserPrincipal userPrincipal, UserUpdateRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<UserListItemResponse> updateRole(Long userId, RoleRequestDto dto) {
        return null;
    }
}
