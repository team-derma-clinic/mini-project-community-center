package com.example.mini_project_community_center.service.auth;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.auth.request.LoginRequestDto;
import com.example.mini_project_community_center.dto.auth.request.PasswordResetRequestDto;
import com.example.mini_project_community_center.dto.auth.request.SignupRequestDto;
import com.example.mini_project_community_center.dto.auth.response.LoginResponseDto;
import com.example.mini_project_community_center.dto.auth.response.PasswordVerifyResponseDto;
import com.example.mini_project_community_center.dto.auth.response.SignupResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

public interface AuthService {
    ResponseDto<SignupResponseDto> register(@Valid SignupRequestDto request);

    ResponseDto<LoginResponseDto> login(@Valid LoginRequestDto request, HttpServletResponse response);

    ResponseDto<Void> logout(HttpServletRequest request, HttpServletResponse response);

    ResponseDto<LoginResponseDto> refreshAccessToken(HttpServletRequest request, HttpServletResponse response);

    ResponseDto<PasswordVerifyResponseDto> verifyPasswordToken(String token);

    ResponseDto<Void> resetPassword(@Valid PasswordResetRequestDto request);

    ResponseDto<Void> sendPasswordResetEmail(String email);
}
