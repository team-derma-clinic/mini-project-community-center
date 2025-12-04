package com.example.mini_project_community_center.service.enrollment.impl;

import com.example.mini_project_community_center.common.enums.enrollment.EnrollmentsStatus;
import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.dto.PageRequestDto;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.enrollment.request.EnrollmentCreateRequest;
import com.example.mini_project_community_center.dto.enrollment.request.EnrollmentReasonRequest;
import com.example.mini_project_community_center.dto.enrollment.response.EnrollmentDetailResponse;
import com.example.mini_project_community_center.dto.enrollment.response.EnrollmentListItemResponse;
import com.example.mini_project_community_center.entity.course.Course;
import com.example.mini_project_community_center.entity.enrollment.Enrollment;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.repository.course.course.CourseRepository;
import com.example.mini_project_community_center.repository.enrollment.EnrollmentRepository;
import com.example.mini_project_community_center.repository.user.UserRepository;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.enrollment.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ResponseDto<EnrollmentDetailResponse> createEnrollment(UserPrincipal userPrincipal, EnrollmentCreateRequest req) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, "해당 유저가 존재하지 않습니다."));

        Course course = courseRepository.findById(req.courseId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 강좌가 존재하지 않습니다. courseId: " + req.courseId()));

        if (enrollmentRepository.existsByCourseIdAndUserId(req.courseId(), userPrincipal.getId())) {
            throw new IllegalArgumentException("이미 해당 강좌에 등록되어 있습니다.");
        }

        Enrollment enrollment = Enrollment.createEnrollment(
                course,
                user,
                EnrollmentsStatus.PENDING,
                null,
                null
        );

        Long countConfirmed = enrollmentRepository.countConfirmedByCourseId(req.courseId());

        if (countConfirmed >= course.getCapacity()) {
            throw new BusinessException(ErrorCode.DB_CONSTRAINT, "이미 정원이 가득 찬 강좌입니다.");
        }

        Enrollment saved = enrollmentRepository.save(enrollment);

        EnrollmentDetailResponse data = EnrollmentDetailResponse.fromDetailEntity(saved);

        return ResponseDto.success(data);
    }

    @Override
    public ResponseDto<List<EnrollmentListItemResponse>> getMyEnrollments(UserPrincipal userPrincipal, PageRequestDto page) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, "해당 유저가 존재하지 않습니다."));

        Pageable pageable = page.toPageable();

        Page<Enrollment> enrollments = enrollmentRepository.findByUserOrderByEnrolledAtDesc(user, pageable);

        List<EnrollmentListItemResponse> list = enrollments.getContent().stream()
                .map(EnrollmentListItemResponse::fromListEntity)
                .toList();

        return ResponseDto.success(list);
    }

    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public ResponseDto<List<EnrollmentListItemResponse>> getAllEnrollments(PageRequestDto page, EnrollmentsStatus status) {
        Pageable pageable = page.toPageable();

        Page<Enrollment> enrollments = enrollmentRepository.findByStatus(status, pageable);
        List<EnrollmentListItemResponse> list = enrollments.getContent().stream()
                .map(EnrollmentListItemResponse::fromListEntity)
                .toList();

        return ResponseDto.success(list);
    }

    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public ResponseDto<EnrollmentDetailResponse> getEnrollmentById(UserPrincipal userPrincipal, Long enrollmentId) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, "해당 유저가 존재하지 않습니다."));

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 결제 정보가 존재하지 않습니다. enrollmentId: " + enrollmentId));

        EnrollmentDetailResponse data = EnrollmentDetailResponse.fromDetailEntity(enrollment);

        return ResponseDto.success(data);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN') or @authz.isOwner(#enrollmentId, authentication)")
    public ResponseDto<EnrollmentDetailResponse> cancelEnrollment(UserPrincipal userPrincipal, Long enrollmentId, EnrollmentReasonRequest req) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, "해당 유저가 존재하지 않습니다."));

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 결제 정보가 존재하지 않습니다. Id: " + enrollmentId));

        if (enrollment.getStatus() == EnrollmentsStatus.CANCELED ||
            enrollment.getStatus() == EnrollmentsStatus.REFUNDED
        ) {
            throw new BusinessException(ErrorCode.DB_CONSTRAINT, "이미 취소되었거나 환불된 등록입니다.");
        }

        enrollment.cancel(req.cancelReason());

        EnrollmentDetailResponse data = EnrollmentDetailResponse.fromDetailEntity(enrollment);

        return ResponseDto.success(data);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public ResponseDto<EnrollmentDetailResponse> refundEnrollment(UserPrincipal userPrincipal, Long enrollmentId, EnrollmentReasonRequest req) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, "해당 유저가 존재하지 않습니다."));

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 결제 정보가 존재하지 않습니다. enrollmentId: " + enrollmentId));

        if (enrollment.getStatus() != EnrollmentsStatus.CANCELED) {
            throw new BusinessException(ErrorCode.DB_CONSTRAINT, "취소되지 않은 등록은 환불할 수 없습니다.");
        }

        enrollment.refund(req.cancelReason());

        EnrollmentDetailResponse data = EnrollmentDetailResponse.fromDetailEntity(enrollment);

        return ResponseDto.success(data);
    }
}
