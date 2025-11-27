package com.example.mini_project_community_center.controller;

import com.example.mini_project_community_center.common.apis.AttendanceApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.attendance.request.AttendanceCreateRequest;
import com.example.mini_project_community_center.dto.attendance.request.AttendanceUpdateRequest;
import com.example.mini_project_community_center.dto.attendance.response.AttendanceDetailResponse;
import com.example.mini_project_community_center.dto.attendance.response.AttendanceListItemResponse;
import com.example.mini_project_community_center.service.attendance.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AttendanceApi.ROOT)
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    // POST /api/v1/attendance
    @PostMapping
    public ResponseEntity<ResponseDto<AttendanceDetailResponse>> upsertAttendance(
            @Valid @RequestBody AttendanceCreateRequest req
    ) {
        ResponseDto<AttendanceDetailResponse> data = attendanceService.upsertAttendance(req);
        return ResponseEntity.ok(data);
    }

    // GET /api/v1/attendance/{attendanceId}
    @GetMapping(AttendanceApi.ID_ONLY)
    public ResponseEntity<ResponseDto<AttendanceDetailResponse>> getAttendanceDetail(
            @PathVariable Long attendanceId
    ) {
        ResponseDto<AttendanceDetailResponse> data = attendanceService.getAttendanceDetail(attendanceId);
        return ResponseEntity.ok(data);
    }

    // PUT /api/v1/attendance/{attendanceId}
    @PutMapping(AttendanceApi.ID_ONLY)
    public ResponseEntity<ResponseDto<AttendanceDetailResponse>> updateAttendance(
            @PathVariable Long attendanceId,
            @Valid @RequestBody AttendanceUpdateRequest req
    ) {
        ResponseDto<AttendanceDetailResponse> data = attendanceService.updateAttendance(attendanceId, req);
        return ResponseEntity.ok(data);
    }

    // GET /api/v1/courses/{courseId}/attendance
    @GetMapping(AttendanceApi.COURSE_LIST)
    public ResponseEntity<ResponseDto<List<AttendanceListItemResponse>>> getCourseAttendance(
            @PathVariable Long courseId,
            @RequestParam(required = false) Long sessionId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort
    ) {
        ResponseDto<List<AttendanceListItemResponse>> data = attendanceService.getCourseAttendance(courseId, sessionId, userId, page, size, sort);
        return ResponseEntity.ok(data);
    }

    // GET /api/v1/sessions/{courseId}/attendance
    @GetMapping(AttendanceApi.SESSION_LIST)
    public ResponseEntity<ResponseDto<List<AttendanceListItemResponse>>> getSessionAttendance(
            @PathVariable Long sessionId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort
    ) {
        ResponseDto<List<AttendanceListItemResponse>> data = attendanceService.getSessionAttendance(sessionId, page, size, sort);
        return ResponseEntity.ok(data);
    }
}
