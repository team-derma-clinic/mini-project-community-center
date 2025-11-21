package com.example.mini_project_community_center.entity;

import com.example.mini_project_community_center.common.enums.CourseSessionsStatus;
import com.example.mini_project_community_center.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @Column(name = "status", length = 60, nullable = false)
    private CourseSessionsStatus status = CourseSessionsStatus.SCHEDULED;

    public static CourseSession create(
            Course course,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String room,
            CourseSessionsStatus status
    ) {
        CourseSession session = new CourseSession();
        session.course = course;
        session.startTime = startTime;
        session.endTime = endTime;
        session.room = (room != null ? room : "");
        session.status = (status != null ? status : CourseSessionsStatus.SCHEDULED);

        session.validateTime();
        return session;
    }

    public void update(
            LocalDateTime startTime,
            LocalDateTime endTime,
            String room,
            CourseSessionsStatus status
    ) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = (room != null ? room : this.room);
        this.status = (status != null ? status : this.status);
        this.validateTime();
    }

    public void changeStatus(CourseSessionsStatus newStatus) {
        if(newStatus == null) return;
        this.status = newStatus;
    }

    public void cancel() {
        this.status = CourseSessionsStatus.CANCELED;
    }

    public void changeRoom(String newRoom) {
        this.room = (newRoom != null ? newRoom : "");
    }

    private void validateTime() {
        if(this.startTime.isAfter(this.endTime)) {
            throw new IllegalArgumentException("시작 시간은 종료 시간보다 이전이어야 합니다.");
        }
    }

    public boolean isOverlapped(LocalDateTime newStart, LocalDateTime newEnd) {
        return !(newEnd.isBefore(this.startTime) || newStart.isAfter(this.endTime));
    }

    public boolean isOnDate(LocalDate date) {
        return this.startTime.toLocalDate().equals(date);
    }
}
