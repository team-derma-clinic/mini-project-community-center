package com.example.mini_project_community_center.service.enrollment.impl;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.enrollment.request.EnrollmentCreateRequest;
import com.example.mini_project_community_center.dto.enrollment.request.EnrollmentReasonRequest;
import com.example.mini_project_community_center.dto.enrollment.response.EnrollmentDetailResponse;
import com.example.mini_project_community_center.dto.enrollment.response.EnrollmentListItemResponse;
import com.example.mini_project_community_center.security.UserPrincipal;
import com.example.mini_project_community_center.service.enrollment.EnrollmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Override
    public ResponseDto<List<EnrollmentListItemResponse>> getMyEnrollments(UserPrincipal user) {
        return null;
    }

    @Override
    public ResponseDto<EnrollmentDetailResponse> createEnrollment(UserPrincipal userPrincipal, EnrollmentCreateRequest req) {
        return null;
    }

    @Override
    public ResponseDto<List<EnrollmentListItemResponse>> getAllEnrollments() {
        return null;
    }

    @Override
    public ResponseDto<EnrollmentDetailResponse> getEnrollmentById(UserPrincipal userPrincipal, Long enrollmentId) {
        return null;
    }

    @Override
    public ResponseDto<EnrollmentDetailResponse> cancelEnrollment(UserPrincipal userPrincipal, Long enrollmentId, EnrollmentReasonRequest req) {
        return null;
    }

    @Override
    public ResponseDto<EnrollmentDetailResponse> refundEnrollment(UserPrincipal userPrincipal, Long enrollmentId, EnrollmentReasonRequest req) {
        return null;
    }
}
