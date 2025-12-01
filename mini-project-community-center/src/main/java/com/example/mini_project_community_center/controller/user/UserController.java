package com.example.mini_project_community_center.controller.user;

import com.example.mini_project_community_center.common.apis.UserApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.user.response.UserDetailResponseDto;
import com.example.mini_project_community_center.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserApi.ROOT)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(UserApi.ME)
    public ResponseEntity<ResponseDto<UserDetailResponseDto>>
}
