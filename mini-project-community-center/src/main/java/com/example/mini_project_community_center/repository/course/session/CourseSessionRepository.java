package com.example.mini_project_community_center.repository.course.session;

import com.example.mini_project_community_center.dto.course.session.request.SessionSearchRequest;
import com.example.mini_project_community_center.entity.course.session.CourseSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSession, Long>, CourseSessionRepositoryCustom {
    boolean existsByCourseId(Long courseId);

    Page<CourseSession> searchSessions(Long courseId, SessionSearchRequest req, Pageable pageable);
}
