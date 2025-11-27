package com.example.mini_project_community_center.controller.course;

import com.example.mini_project_community_center.common.apis.ApiBase;
import com.example.mini_project_community_center.common.apis.CourseSessionApi;
import com.example.mini_project_community_center.common.apis.SessionApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.course_session.request.SessionCreateRequest;
import com.example.mini_project_community_center.dto.course_session.request.SessionStatusUpdateRequest;
import com.example.mini_project_community_center.dto.course_session.request.SessionUpdateRequest;
import com.example.mini_project_community_center.dto.course_session.response.SessionDetailResponse;
import com.example.mini_project_community_center.dto.course_session.response.SessionListItemResponse;
import com.example.mini_project_community_center.security.UserPrincipal;
import com.example.mini_project_community_center.service.course.CourseSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiBase.BASE)
@RequiredArgsConstructor
public class CourseSessionController {
    private final CourseSessionService sessionService;

    // 세션 생성(STAFF/ADMIN)
    @PostMapping(CourseSessionApi.ROOT + SessionApi.ONLY_BY_ID)
    public ResponseEntity<ResponseDto<SessionDetailResponse>> createSession(
            @AuthenticationPrincipal UserPrincipal userPrincipal,,
            @PathVariable Long courseId,
            @Valid @RequestBody SessionCreateRequest req
            ) {
        ResponseDto<SessionDetailResponse> data = sessionService.createSession(courseId, req);
        return ResponseEntity.ok(data);
    }

    // 세션 목록/검색(Public)
    @GetMapping(CourseSessionApi.ROOT)
    public ResponseEntity<ResponseDto<Page<SessionListItemResponse>>> getSessions(
            @PathVariable Long courseId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) Integer weekday,
            @RequestParam(required = false) String timeRange,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startTime,asc") String sort
    ) {
        ResponseDto<Page<SessionListItemResponse>> data = sessionService.getSessions(
                courseId, from, to, weekday, timeRange, q, page, size, sort
        );
        return ResponseEntity.ok(data);
    }

    // 세션 상세 조회(Public)
    @GetMapping(SessionApi.BY_SESSION_ID)
    public ResponseEntity<ResponseDto<SessionDetailResponse>> getSessionDetail(
            @PathVariable Long sessionId
    ) {
        ResponseDto<SessionDetailResponse> data = sessionService.getSessionDetail(sessionId);
        return ResponseEntity.ok(data);
    }

    // 세션 수정(STAFF/ADMIN)
    @PutMapping(SessionApi.BY_SESSION_ID)
    public ResponseEntity<ResponseDto<SessionDetailResponse>> updateSession(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long sessionId,
            @Valid @RequestBody SessionUpdateRequest req
            ) {
        ResponseDto<SessionDetailResponse> data = sessionService.updateSession(sessionId, req);
        return ResponseEntity.ok(data);
    }

    // 세션 상태 변경(STAFF/ADMIN)
    @PutMapping(SessionApi.STATUS)
    public ResponseEntity<ResponseDto<SessionDetailResponse>> updateSessionStatus(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody SessionStatusUpdateRequest req
            ) {
        ResponseDto<SessionDetailResponse> data= sessionService.updateSessionStatus(req);
        return ResponseEntity.ok(data);
    }

    // 세션 삭제(STAFF/ADMIN)
    @DeleteMapping(SessionApi.BY_SESSION_ID)
    public ResponseEntity<ResponseDto<Void>> deleteSession(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long sessionId,
            @RequestParam(defaultValue = "false") boolean hardDelete
    ) {
        ResponseDto<Void> data = sessionService.deleteSession(sessionId, hardDelete);
        return ResponseEntity.ok(data);
    }

}
