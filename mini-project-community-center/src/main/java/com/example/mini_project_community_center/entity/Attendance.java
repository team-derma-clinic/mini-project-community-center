package com.example.mini_project_community_center.entity;

import com.example.mini_project_community_center.common.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "attendance",
        uniqueConstraints = {@UniqueConstraint(name = "uk_attend_once", columnNames = {"session_id", "user_id"})},
        indexes = {@Index(name = "idx_attend_session", columnList = "session_id, status")}
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", foreignKey = @ForeignKey(name = "fk_attendance_session"), nullable = false)
    private CourseSession session;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_attendance_user"), nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 15)
    private AttendanceStatus status = AttendanceStatus.ABSENT;

    @Column(name = "note", length = 255)
    private String note;

    @Column(name = "marked_at", nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime markedAt;

    public static Attendance createAttendance(
            CourseSession session,
            User user,
            AttendanceStatus status,
            String note,
            LocalDateTime markedAt
    ) {
        Attendance attendance = new Attendance();
        attendance.session = session;
        attendance.user = user;
        attendance.status = status;
        attendance.note = note;
        attendance.markedAt = markedAt;
    }

    public void updateStatus(AttendanceStatus status) {
        this.status = status;
    }

    public void updateNote(String note) {
        this.note = note;
    }

}
