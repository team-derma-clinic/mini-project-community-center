package com.example.mini_project_community_center.service.attendance;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.attendance.request.AttendanceCreateRequest;
import com.example.mini_project_community_center.dto.attendance.request.AttendanceUpdateRequest;
import com.example.mini_project_community_center.dto.attendance.response.AttendanceDetailResponse;
import com.example.mini_project_community_center.security.UserPrincipal;
import jakarta.validation.Valid;

import java.util.List;

public interface AttendanceService {
    ResponseDto<AttendanceDetailResponse> upsertAttendance(UserPrincipal userPrincipal, @Valid AttendanceCreateRequest req);
    ResponseDto<AttendanceDetailResponse> getAttendanceDetail(Long attendanceId);
    ResponseDto<AttendanceDetailResponse> updateAttendance(UserPrincipal userPrincipal, Long attendanceId, @Valid AttendanceUpdateRequest req);
    ResponseDto<List<AttendanceDetailResponse>> getCourseAttendance(Long courseId, Long sessionId, Long userId, Integer page, Integer size, String sort);
    ResponseDto<List<AttendanceDetailResponse>> getSessionAttendance(Long sessionId, Integer page, Integer size, String sort);
}
