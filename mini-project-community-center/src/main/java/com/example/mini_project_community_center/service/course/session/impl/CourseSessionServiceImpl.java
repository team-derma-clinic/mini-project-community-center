package com.example.mini_project_community_center.service.course.session.impl;

import com.example.mini_project_community_center.common.enums.course.CourseSessionsStatus;
import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.dto.PageRequestDto;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.course.session.request.SessionCreateRequest;
import com.example.mini_project_community_center.dto.course.session.request.SessionSearchRequest;
import com.example.mini_project_community_center.dto.course.session.request.SessionStatusUpdateRequest;
import com.example.mini_project_community_center.dto.course.session.request.SessionUpdateRequest;
import com.example.mini_project_community_center.dto.course.session.response.SessionDetailResponse;
import com.example.mini_project_community_center.dto.course.session.response.SessionListItemResponse;
import com.example.mini_project_community_center.entity.course.Course;
import com.example.mini_project_community_center.entity.course.session.CourseSession;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.repository.course.course.CourseRepository;
import com.example.mini_project_community_center.repository.course.session.CourseSessionRepository;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.course.session.CourseSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseSessionServiceImpl implements CourseSessionService {
    private final CourseSessionRepository sessionRepository;
    private final CourseRepository courseRepository;

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public ResponseDto<SessionDetailResponse> createSession(UserPrincipal userPrincipal, Long courseId, SessionCreateRequest req) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND,
                        "해당 강좌가 존재하지 않습니다. courseId: " + courseId));

        validateDateRange(req.startTime(), req.endTime());

        CourseSession session = CourseSession.create(
                course,
                DateUtils.parseLocalDateTime(req.startTime()),
                DateUtils.parseLocalDateTime(req.endTime()),
                req.room(),
                req.status()
        );

        CourseSession saved = sessionRepository.save(session);
        SessionDetailResponse data = SessionDetailResponse.fromEntity(saved);

        return ResponseDto.success(data);
    }

    @Override
    public ResponseDto<Page<SessionListItemResponse>> getSessions(SessionSearchRequest searchReq, PageRequestDto pageReq) {
        Pageable pageable = pageReq.toPageable();

        LocalDate start = LocalDate.parse(searchReq.from());
        LocalDate end = LocalDate.parse(searchReq.to());
        if(start.isAfter(end)) throw new IllegalArgumentException("시작 날짜가 종료 날짜 이후일 수 없습니다.");

        Page<CourseSession> pageResult = sessionRepository.searchSessions(searchReq, pageable);

        Page<SessionListItemResponse> data = pageResult.map(session -> new SessionListItemResponse(
                session.getId(),
                session.getCourse().getId(),
                DateUtils.toKstString(session.getStartTime()),
                DateUtils.toKstString(session.getEndTime()),
                session.getRoom(),
                session.getStatus()
        ));

        return ResponseDto.success(data);
    }

    @Override
    public ResponseDto<SessionDetailResponse> getSessionDetail(Long sessionId) {
        CourseSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND,
                        "해당 세션이 존재하지 않습니다. sessionId: " + sessionId));

        SessionDetailResponse data = SessionDetailResponse.fromEntity(session);

        return ResponseDto.success(data);
    }

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public ResponseDto<SessionDetailResponse> updateSession(UserPrincipal userPrincipal, Long sessionId, SessionUpdateRequest req) {
        CourseSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND,
                        "해당 세션이 존재하지 않습니다. sessionId: " + sessionId));

        if (req.startTime() != null && req.endTime() != null) {
            validateDateRange(req.startTime(), req.endTime());
        }

        session.update(
                DateUtils.parseLocalDateTime(req.startTime()),
                DateUtils.parseLocalDateTime(req.endTime()),
                req.room(),
                req.status()
        );

        SessionDetailResponse data = SessionDetailResponse.fromEntity(session);

        return ResponseDto.success(data);
    }

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public ResponseDto<SessionDetailResponse> updateSessionStatus(UserPrincipal userPrincipal, Long sessionId, SessionStatusUpdateRequest req) {
        CourseSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND,
                        "해당 세션이 존재하지 않습니다. sessionId: " + sessionId));

        session.changeStatus(req.status());

        SessionDetailResponse data = SessionDetailResponse.fromEntity(session);

        return ResponseDto.success(data);
    }

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public ResponseDto<Void> deleteSession(UserPrincipal userPrincipal, Long sessionId, boolean hardDelete) {
        CourseSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND,
                        "해당 세션이 존재하지 않습니다. sessionId: " + sessionId));

        if (hardDelete) {
            sessionRepository.delete(session);
        } else {
            session.changeStatus(CourseSessionsStatus.CANCELED);
            sessionRepository.save(session);
        }

        return ResponseDto.success(null);
    }

    private void validateDateRange(String startTime, String endTime) {
        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("시작 날짜는 종료 날짜보다 빠를 수 없습니다.");
        }
    }
}
