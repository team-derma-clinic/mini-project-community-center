package com.example.mini_project_community_center.repository.course.session;

import com.example.mini_project_community_center.entity.course.CourseSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSession, Long> {
}
