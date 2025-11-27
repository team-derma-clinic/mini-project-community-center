package com.example.mini_project_community_center.repository.attendance;

import com.example.mini_project_community_center.entity.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findBySessionIdAndUserId(Long sessionId, Long userId);

    List<Attendance> findBySessionId(Long sessionId);

    List<Attendance> findBySession_CourseId(Long courseId);

    boolean existsBySessionIdAndUserId(Long sessionId, Long userId);
}
