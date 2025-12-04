package com.example.mini_project_community_center.handler;

import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JsonAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {

        log.warn("[AccessDeniedHandler] 권한 부족: {}", accessDeniedException.getMessage());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");

        ResponseDto<?> body = ResponseDto.failure(
                "접근 권한이 없습니다.",
                HttpStatus.FORBIDDEN.value(),
                ErrorCode.ACCESS_DENIED.name()
        );

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
