package com.example.mini_project_community_center.service.user;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.user.request.PasswordChangeRequestDto;
import com.example.mini_project_community_center.dto.user.request.UserUpdateRequestDto;
import com.example.mini_project_community_center.dto.user.response.MeResponseDto;
import com.example.mini_project_community_center.dto.user.response.UserDetailResponseDto;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import jakarta.validation.Valid;

public interface UserService {

    ResponseDto<MeResponseDto> getMe(Long userId);

    ResponseDto<UserDetailResponseDto> getUserById(Long userId);

    ResponseDto<Void> updatePassword(UserPrincipal userPrincipal, @Valid PasswordChangeRequestDto dto);

    ResponseDto<MeResponseDto> updateUserInfo(UserPrincipal userPrincipal, UserUpdateRequestDto dto);
}
