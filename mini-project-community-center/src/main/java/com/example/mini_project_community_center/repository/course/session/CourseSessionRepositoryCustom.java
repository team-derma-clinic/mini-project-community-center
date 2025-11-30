package com.example.mini_project_community_center.repository.course.session;

import com.example.mini_project_community_center.dto.course.session.request.SessionSearchRequest;
import com.example.mini_project_community_center.entity.course.session.CourseSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseSessionRepositoryCustom {
    Page<CourseSession> searchSessions(SessionSearchRequest req, Pageable pageable);
}
