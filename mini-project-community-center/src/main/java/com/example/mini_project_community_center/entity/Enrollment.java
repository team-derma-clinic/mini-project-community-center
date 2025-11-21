package com.example.mini_project_community_center.entity;

import com.example.mini_project_community_center.common.enums.EnrollmentsStatus;
import com.example.mini_project_community_center.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_enroll_once", columnNames = { "course_id", "user_id" }),
        },
        indexes = {
            @Index(name = "idx_enroll_course_status", columnList = "course_id, status"),
            @Index(name = "idx_enroll_user", columnList = "user_id, status"),
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enrollment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_enrollment_course"), nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_enrollment_user"), nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private EnrollmentsStatus status = EnrollmentsStatus.PENDING;

    @Column(name = "cancel_reason", length = 200)
    private String cancelReason;

    @Column(name = "enrolled_at", nullable = false, updatable = false)
    private LocalDateTime enrolledAt;

    public static Enrollment createEnrollment(Course course, User user, EnrollmentsStatus status, String cancelReason, LocalDateTime enrolledAt) {
        Enrollment enrollment = new Enrollment();
        enrollment.course = course;
        enrollment.user = user;
        enrollment.status = status;
        enrollment.cancelReason = cancelReason;
        enrollment.enrolledAt = enrolledAt != null ? enrolledAt: LocalDateTime.now();
        return enrollment;
    }

    public void cancel(String reason) {
        this.status = EnrollmentsStatus.CANCELED;
        this.cancelReason = reason;
    }

    public void refund(String reason) {
        this.status = EnrollmentsStatus.REFUNDED;
        this.cancelReason = reason;
    }

    public void markPaid() {
        this.status = EnrollmentsStatus.CONFIRMED;
    }
}
