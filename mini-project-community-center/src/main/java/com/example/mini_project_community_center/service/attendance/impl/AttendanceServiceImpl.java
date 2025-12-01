package com.example.mini_project_community_center.service.attendance.impl;

import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.attendance.request.AttendanceCreateRequest;
import com.example.mini_project_community_center.dto.attendance.request.AttendanceUpdateRequest;
import com.example.mini_project_community_center.dto.attendance.response.AttendanceDetailResponse;
import com.example.mini_project_community_center.entity.attendance.Attendance;
import com.example.mini_project_community_center.entity.course.session.CourseSession;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.repository.attendance.AttendanceRepository;
import com.example.mini_project_community_center.repository.course.session.CourseSessionRepository;
import com.example.mini_project_community_center.repository.user.UserRepository;
import com.example.mini_project_community_center.security.UserPrincipal;
import com.example.mini_project_community_center.service.attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final CourseSessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public ResponseDto<AttendanceDetailResponse> upsertAttendance(UserPrincipal userPrincipal, AttendanceCreateRequest req) {
        CourseSession session = sessionRepository.findById(req.sessionId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        Long currentUserId = userPrincipal.getUser().getId();

        if (req.userId().equals(currentUserId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED, "본인의 출석은 등록/수정할 수 없습니다.");
        }

        boolean isInstructor = session.getCourse().getInstructors().stream()
                .anyMatch(ci -> ci.getInstructor().getId().equals(currentUserId));

        if (!isInstructor) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED, "해당 강좌의 강사만 출석을 등록/수정할 수 있습니다.");
        }

        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "사용자를 찾을 수 없습니다. userId: " + req.userId()));

        Attendance attendance = attendanceRepository
                .findBySessionIdAndUserId(req.sessionId(), req.userId())
                .orElse(null);

        if (attendance == null) {
            attendance = Attendance.createAttendance(
                    session,
                    user,
                    req.status(),
                    null,
                    null
            );
        } else {
            attendance.updateStatus(req.status());
        }

        Attendance saved = attendanceRepository.save(attendance);

        return ResponseDto.success(toDetailResponse(saved));
    }

    @Override
    public ResponseDto<AttendanceDetailResponse> getAttendanceDetail(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "출석을 찾을 수 없습니다. attendanceId: " + attendanceId));

        return ResponseDto.success(toDetailResponse(attendance));
    }

    @Transactional
    @Override
    public ResponseDto<AttendanceDetailResponse> updateAttendance(UserPrincipal userPrincipal, Long attendanceId, AttendanceUpdateRequest req) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "출석을 찾을 수 없습니다. attendanceId: " + attendanceId));

        Long currentUserId = userPrincipal.getUser().getId();
        Long attendanceUserId = attendance.getUser().getId();

        if (attendanceUserId.equals(currentUserId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED, "본인의 출석은 수정할 수 없습니다.");
        }

        boolean isInstructor = attendance.getSession().getCourse().getInstructors().stream()
                .anyMatch(ci -> ci.getInstructor().getId().equals(currentUserId));

        if (!isInstructor) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED, "해당 강좌의 강사만 출석을 수정할 수 있습니다.");
        }

        if (req.status() != null) {
            attendance.updateStatus(req.status());
        }

        if (req.note() != null) {
            attendance.updateNote(req.note());
        }

        Attendance saved = attendanceRepository.save(attendance);

        return ResponseDto.success(toDetailResponse(saved));
    }

    @Override
    public ResponseDto<List<AttendanceDetailResponse>> getCourseAttendance(Long courseId, Long sessionId, Long userId, Integer page, Integer size, String sort) {
        List<Attendance> attendances;

        if (sessionId != null) {
            attendances = attendanceRepository.findBySessionId(sessionId);
        } else {
            attendances = attendanceRepository.findBySession_CourseId(courseId);
        }

        if (userId != null) {
            attendances = attendances.stream()
                    .filter(a -> a.getUser().getId().equals(userId))
                    .collect(Collectors.toList());
        }

        List<AttendanceDetailResponse> items = attendances.stream()
                .map(this::toDetailResponse)
                .collect(Collectors.toList());

        return ResponseDto.success(items);
    }

    @Override
    public ResponseDto<List<AttendanceDetailResponse>> getSessionAttendance(Long sessionId, Integer page, Integer size, String sort) {
        List<Attendance> attendances = attendanceRepository.findBySessionId(sessionId);

        List<AttendanceDetailResponse> items = attendances.stream()
                .map(this::toDetailResponse)
                .collect(Collectors.toList());

        return ResponseDto.success(items);
    }

    private AttendanceDetailResponse toDetailResponse(Attendance attendance) {
        return new AttendanceDetailResponse(
                attendance.getId(),
                attendance.getSession().getId(),
                attendance.getUser().getId(),
                attendance.getUser().getName(),
                attendance.getStatus(),
                attendance.getNote(),
                DateUtils.toKstString(attendance.getMarkedAt())
        );
    }
}
