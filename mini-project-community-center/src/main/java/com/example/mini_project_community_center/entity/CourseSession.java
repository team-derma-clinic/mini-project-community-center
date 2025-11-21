package com.example.mini_project_community_center.entity;

import com.example.mini_project_community_center.common.enums.CourseStatus;
import com.example.mini_project_community_center.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "course_sessions",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_session_unique", columnNames = "course_id, start_time, end_time")
        },
        indexes = {
                @Index(name = "idx_sessions_time", columnList = "start_time, end_time")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CourseSession extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_course_session_course"))
    private Course course;

    @Column(name = "start_time ", nullable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime startTime;

    @Column(name = "end_time ", nullable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime endTime;

    @Column(name = "room", length = 60)
    private String room;

    @Column(name = "status", length = 60)
    private CourseStatus status;

}
