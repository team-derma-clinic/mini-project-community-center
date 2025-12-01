package com.example.mini_project_community_center.controller.user;

import com.example.mini_project_community_center.common.apis.UserApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.role.RoleRequestDto;
import com.example.mini_project_community_center.dto.user.request.UserUpdateRequestDto;
import com.example.mini_project_community_center.dto.user.response.UserDetailResponseDto;
import com.example.mini_project_community_center.dto.user.response.UserListItemResponse;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UserApi.ROOT)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(UserApi.ME)
    public ResponseEntity<ResponseDto<UserDetailResponseDto>> me(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        ResponseDto<UserDetailResponseDto> data = userService.getMe(userPrincipal.getId());
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping(UserApi.BY_ID)
    public ResponseEntity<ResponseDto<UserDetailResponseDto>> getById(
            @PathVariable Long userId
    ){
        ResponseDto<UserDetailResponseDto> data = userService.getUserById(userId);
        return ResponseEntity.status(data.getStatus()) .body(data);
    }

    @PutMapping(UserApi.ME)
    public ResponseEntity<ResponseDto<UserListItemResponse>> updateUserInfo(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @RequestBody UserUpdateRequestDto dto
    ){
        ResponseDto<UserListItemResponse> data = userService.updateUserInfo(userPrincipal, dto);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @PutMapping(UserApi.ROLES)
    public ResponseEntity<ResponseDto<UserListItemResponse>> updateRoles(
            @PathVariable Long userId,
            @RequestBody RoleRequestDto dto
    ){
      ResponseDto<UserListItemResponse> data = userService.updateRole(userId, dto);
      return ResponseEntity.status(data.getStatus()).body(data);
    }

}
