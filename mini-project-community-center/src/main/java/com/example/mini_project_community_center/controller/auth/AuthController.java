package com.example.mini_project_community_center.controller.auth;

import com.example.mini_project_community_center.common.apis.AuthApi;
import com.example.mini_project_community_center.common.enums.user.RoleStatus;
import com.example.mini_project_community_center.common.enums.user.RoleType;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.auth.request.LoginRequestDto;
import com.example.mini_project_community_center.dto.auth.request.PasswordResetRequestDto;
import com.example.mini_project_community_center.dto.auth.request.SignupRequestDto;
import com.example.mini_project_community_center.dto.auth.response.LoginResponseDto;
import com.example.mini_project_community_center.dto.auth.response.PasswordVerifyResponseDto;
import com.example.mini_project_community_center.dto.auth.response.SignupResponseDto;
import com.example.mini_project_community_center.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AuthApi.ROOT)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(AuthApi.REGISTER_STUDENT)
    public ResponseEntity<ResponseDto<SignupResponseDto>> registerStudent(
            @Valid @RequestBody SignupRequestDto request
    ){
        ResponseDto<SignupResponseDto> data = authService.register(
                request,
                RoleType.STUDENT,
                RoleStatus.APPROVED
        );
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @PostMapping(AuthApi.REGISTER_INSTRUCTOR)
    public ResponseEntity<ResponseDto<SignupResponseDto>> registerInstructor(
            @Valid @RequestBody SignupRequestDto request
    ){
        ResponseDto<SignupResponseDto> data = authService.register(
                request,
                RoleType.INSTRUCTOR,
                RoleStatus.PENDING
        );
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @PostMapping(AuthApi.REGISTER_STAFF)
    public ResponseEntity<ResponseDto<SignupResponseDto>> registerStaff(
            @Valid @RequestBody SignupRequestDto request
    ){
        ResponseDto<SignupResponseDto> data = authService.register(
                request,
                RoleType.STAFF,
                RoleStatus.PENDING
        );
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @PostMapping(AuthApi.LOGIN)
    public ResponseEntity<ResponseDto<LoginResponseDto>> login(
            @Valid @RequestBody LoginRequestDto request,
            HttpServletResponse response
    ){
        ResponseDto<LoginResponseDto> data = authService.login(request, response);
        return ResponseEntity.status(response.getStatus()).body(data);
    }

    @PostMapping(AuthApi.LOGOUT)
    public ResponseEntity<ResponseDto<Void>> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ){
        ResponseDto<Void> data = authService.logout(request, response);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @PostMapping(AuthApi.REFRESH)
    public ResponseEntity<ResponseDto<LoginResponseDto>> refresh(
        HttpServletRequest request,
        HttpServletResponse response
    ){
        ResponseDto<LoginResponseDto> data = authService.refreshAccessToken(request, response);
        return ResponseEntity.status(response.getStatus()).body(data);
    }

    @PostMapping(AuthApi.PASSWORD_EMAIL)
    public ResponseEntity<ResponseDto<Void>> passwordResetEmail(
        @RequestParam String email
    ){
        ResponseDto<Void> data = authService.sendPasswordResetEmail(email);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    // 비밀번호 재설정 토큰 확인
    @GetMapping(AuthApi.PASSWORD_VERIFY)
    public ResponseEntity<ResponseDto<PasswordVerifyResponseDto>> passwordVerify(
            @RequestParam("token") String token
    ){
        ResponseDto<PasswordVerifyResponseDto> data = authService.verifyPasswordToken(token);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    //비밀번호 재설정
    @PostMapping(AuthApi.PASSWORD_RESET)
    public ResponseEntity<ResponseDto<Void>> passwordReset(
            @Valid @RequestBody PasswordResetRequestDto request
    ){
        ResponseDto<Void> data = authService.resetPassword(request);
        return ResponseEntity.status(data.getStatus()).body(data);
    }
}
