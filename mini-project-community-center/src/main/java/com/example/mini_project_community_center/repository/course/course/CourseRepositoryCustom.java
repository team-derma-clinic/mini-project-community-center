package com.example.mini_project_community_center.repository.course.course;

import com.example.mini_project_community_center.dto.course.request.CourseSearchRequest;
import com.example.mini_project_community_center.entity.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseRepositoryCustom {
    Page<Course> searchCourses(CourseSearchRequest req, Pageable pageable);
}
