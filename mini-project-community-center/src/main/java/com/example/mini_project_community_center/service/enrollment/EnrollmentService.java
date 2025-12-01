package com.example.mini_project_community_center.service.enrollment;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.enrollment.request.EnrollmentReasonRequest;
import com.example.mini_project_community_center.dto.enrollment.request.EnrollmentCreateRequest;
import com.example.mini_project_community_center.dto.enrollment.response.EnrollmentDetailResponse;
import com.example.mini_project_community_center.dto.enrollment.response.EnrollmentListItemResponse;
import com.example.mini_project_community_center.security.UserPrincipal;
import jakarta.validation.Valid;

import java.util.List;

public interface EnrollmentService {
    ResponseDto<List<EnrollmentListItemResponse>> getMyEnrollments(UserPrincipal user);

    ResponseDto<EnrollmentDetailResponse> createEnrollment(UserPrincipal userPrincipal, @Valid EnrollmentCreateRequest req);

    ResponseDto<List<EnrollmentListItemResponse>> getAllEnrollments();

    ResponseDto<EnrollmentDetailResponse> getEnrollmentById(UserPrincipal userPrincipal, Long enrollmentId);

    ResponseDto<EnrollmentDetailResponse> cancelEnrollment(UserPrincipal userPrincipal, Long enrollmentId, @Valid EnrollmentReasonRequest req);

    ResponseDto<EnrollmentDetailResponse> refundEnrollment(UserPrincipal userPrincipal, Long enrollmentId, @Valid EnrollmentReasonRequest req);
}
