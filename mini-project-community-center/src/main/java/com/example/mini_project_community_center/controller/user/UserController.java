package com.example.mini_project_community_center.controller.user;

import com.example.mini_project_community_center.common.apis.UserApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.user.request.PasswordChangeRequestDto;
import com.example.mini_project_community_center.dto.user.request.UserUpdateRequestDto;
import com.example.mini_project_community_center.dto.user.response.MeResponseDto;
import com.example.mini_project_community_center.dto.user.response.UserDetailResponseDto;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.user.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ResponseDto<MeResponseDto>> me(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        ResponseDto<MeResponseDto> data = userService.getMe(userPrincipal.getId());
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @PutMapping(UserApi.PASSWORD)
    public ResponseEntity<ResponseDto<Void>> updatePassword(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody PasswordChangeRequestDto dto
    ){
        ResponseDto<Void> data = userService.updatePassword(userPrincipal, dto);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @PutMapping(UserApi.ME)
    public ResponseEntity<ResponseDto<MeResponseDto>> updateUserInfo(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @RequestBody UserUpdateRequestDto dto
    ){
        ResponseDto<MeResponseDto> data = userService.updateUserInfo(userPrincipal, dto);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

}
