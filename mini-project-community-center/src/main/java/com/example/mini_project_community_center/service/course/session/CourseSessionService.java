package com.example.mini_project_community_center.service.course.session;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.course.session.request.SessionCreateRequest;
import com.example.mini_project_community_center.dto.course.session.request.SessionSearchRequest;
import com.example.mini_project_community_center.dto.course.session.request.SessionStatusUpdateRequest;
import com.example.mini_project_community_center.dto.course.session.request.SessionUpdateRequest;
import com.example.mini_project_community_center.dto.course.session.response.SessionDetailResponse;
import com.example.mini_project_community_center.dto.course.session.response.SessionListItemResponse;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface CourseSessionService {
    ResponseDto<SessionDetailResponse> createSession(UserPrincipal userPrincipal, Long courseId, @Valid SessionCreateRequest req);

    ResponseDto<Page<SessionListItemResponse>> getSessions(@Valid SessionSearchRequest req);

    ResponseDto<SessionDetailResponse> getSessionDetail(Long sessionId);

    ResponseDto<SessionDetailResponse> updateSession(UserPrincipal userPrincipal, Long sessionId, @Valid SessionUpdateRequest req);

    ResponseDto<SessionDetailResponse> updateSessionStatus(UserPrincipal userPrincipal, Long sessionId, @Valid SessionStatusUpdateRequest req);

    ResponseDto<Void> deleteSession(UserPrincipal userPrincipal, Long sessionId, boolean hardDelete);
}
