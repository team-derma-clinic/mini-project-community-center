package com.example.mini_project_community_center.controller.enrollment;

import com.example.mini_project_community_center.common.apis.EnrollmentApi;
import com.example.mini_project_community_center.common.enums.enrollment.EnrollmentsStatus;
import com.example.mini_project_community_center.dto.PageRequestDto;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.enrollment.request.EnrollmentReasonRequest;
import com.example.mini_project_community_center.dto.enrollment.request.EnrollmentCreateRequest;
import com.example.mini_project_community_center.dto.enrollment.response.EnrollmentDetailResponse;
import com.example.mini_project_community_center.dto.enrollment.response.EnrollmentListItemResponse;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.enrollment.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EnrollmentApi.ROOT)
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    // 수강 등록 (Student)
    @PostMapping
    public ResponseEntity<ResponseDto<EnrollmentDetailResponse>> createEnrollment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody EnrollmentCreateRequest req
    ) {
        ResponseDto<EnrollmentDetailResponse> data = enrollmentService.createEnrollment(userPrincipal, req);

        return ResponseEntity.ok(data);
    }

    // 내 등록 목록 조회 (Student)
    @GetMapping(EnrollmentApi.ME)
    public ResponseEntity<ResponseDto<List<EnrollmentListItemResponse>>> getMyEnrollments(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @ModelAttribute PageRequestDto page
    ) {
        ResponseDto<List<EnrollmentListItemResponse>> data = enrollmentService.getMyEnrollments(userPrincipal, page);

        return ResponseEntity.ok(data);
    }

    // 전체 등록 목록 (STAFF/ADMIN)
    @GetMapping
    public ResponseEntity<ResponseDto<List<EnrollmentListItemResponse>>> getAllEnrollments(
            @Valid @ModelAttribute PageRequestDto page,
            @RequestParam(required = false) EnrollmentsStatus status
    ) {
        ResponseDto<List<EnrollmentListItemResponse>> data = enrollmentService.getAllEnrollments(page, status);

        return ResponseEntity.ok(data);
    }

    // 등록 상세 조회 (본인/STAFF/ADMIN)
    @GetMapping(EnrollmentApi.BY_ID)
    public ResponseEntity<ResponseDto<EnrollmentDetailResponse>> getEnrollmentById(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long enrollmentId
    ) {
        ResponseDto<EnrollmentDetailResponse> data = enrollmentService.getEnrollmentById(userPrincipal, enrollmentId);

        return ResponseEntity.ok(data);
    }

    // 등록 취소 (본인/STAFF/ADMIN)
    @PutMapping(EnrollmentApi.ENROLLMENT_CANCEL)
    public ResponseEntity<ResponseDto<EnrollmentDetailResponse>> cancelEnrollment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long enrollmentId,
            @Valid @RequestBody EnrollmentReasonRequest req
    ) {
        ResponseDto<EnrollmentDetailResponse> data = enrollmentService.cancelEnrollment(userPrincipal, enrollmentId, req);

        return ResponseEntity.ok(data);
    }

    // 등록 환불 (STAFF/ADMIN)
    @PutMapping(EnrollmentApi.ENROLLMENT_REFUND)
    public ResponseEntity<ResponseDto<EnrollmentDetailResponse>> refundEnrollment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long enrollmentId,
            @Valid @RequestBody EnrollmentReasonRequest req
    ) {
        ResponseDto<EnrollmentDetailResponse> data = enrollmentService.refundEnrollment(userPrincipal, enrollmentId, req);

        return ResponseEntity.ok(data);
    }
}
