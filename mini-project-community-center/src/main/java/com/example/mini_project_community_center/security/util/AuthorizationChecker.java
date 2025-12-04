package com.example.mini_project_community_center.security.util;

import com.example.mini_project_community_center.repository.attendance.AttendanceRepository;
import com.example.mini_project_community_center.repository.course.course.CourseRepository;
import com.example.mini_project_community_center.repository.course.session.CourseSessionRepository;
import com.example.mini_project_community_center.repository.enrollment.EnrollmentRepository;
import com.example.mini_project_community_center.repository.payment.PaymentRepository;
import com.example.mini_project_community_center.repository.review.ReviewRepository;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("authz")
@RequiredArgsConstructor
public class AuthorizationChecker {
    private final AttendanceRepository attendanceRepository;
    private final CourseRepository courseRepository;
    private final CourseSessionRepository courseSessionRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PaymentRepository paymentRepository;
    private final ReviewRepository reviewRepository;

    public boolean isSelf(Long userId, Authentication authentication) {
        if (userId == null) return false;
        Long me = extractUserId(authentication);
        return userId.equals(me);
    }

    // 강좌
    public boolean isInstructorOfCourse(Long courseId, Authentication authentication) {
        Long me =  extractUserId(authentication);
        if (me == null) return false;

        if (!hasRole(authentication, "INSTRUCTOR")) return false;

        return courseRepository.findById(courseId)
                .map(c -> c.getInstructors().stream()
                        .anyMatch(ci -> ci.getInstructor().getId().equals(me)))
                .orElse(false);
    }

    // 세션
    public boolean isInstructorOfSession(Long sessionId, Authentication authentication) {
        Long me =  extractUserId(authentication);
        if (me == null) return false;

        if (!hasRole(authentication, "INSTRUCTOR")) return false;

        return courseSessionRepository.findById(sessionId)
                .map(s -> s.getCourse().getInstructors().stream()
                        .anyMatch(i -> i.getInstructor().getId().equals(me)))
                .orElse(false);
    }

    public boolean canManageCourse(Long sessionId, Authentication authentication) {
        if (hasRole(authentication, "MANAGER") || hasRole(authentication, "STAFF")) return true;

        return isInstructorOfSession(sessionId, authentication);
    }

    // 등록
    public boolean isOwner(Long enrollmentId, Authentication authentication) {
        Long me = extractUserId(authentication);

        if (me == null) return false;

        return enrollmentRepository.findById(enrollmentId)
                .map(e -> e.getUser() != null && me.equals(e.getUser().getId()))
                .orElse(false);
    }

    // 결제
    public boolean isPayer(Long paymentId, Authentication authentication) {
        Long me = extractUserId(authentication);
        if (me == null) return false;

        return paymentRepository.findById(paymentId)
                .map(p -> p.getEnrollment().getUser() != null && me.equals(p.getEnrollment().getUser().getId()))
                .orElse(false);
    }

    // 리뷰
    public boolean isWriter(Long reviewId, Authentication authentication) {
        Long me = extractUserId(authentication);
        if (me == null) return false;

        return reviewRepository.findById(reviewId)
                .map(r -> r.getUser() != null && me.equals(r.getUser().getId()))
                .orElse(false);
    }

    private Long extractUserId(Authentication authentication) {
        if (authentication == null) return null;

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserPrincipal up) {
            return up.getId();
        }

        return null;
    }

    private boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }
}
