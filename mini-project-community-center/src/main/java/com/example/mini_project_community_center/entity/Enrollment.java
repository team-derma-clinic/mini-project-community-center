package com.example.mini_project_community_center.entity;

import com.example.mini_project_community_center.common.enums.EnrollmentsStatus;
import com.example.mini_project_community_center.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@AttributeOverride(
        name = "createdAt",
        column = @Column(name = "enrolled_at", updatable = false)
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
    private EnrollmentsStatus status;

    @Column(name = "cancel_reason", length = 200)
    private String cancelReason;


}
