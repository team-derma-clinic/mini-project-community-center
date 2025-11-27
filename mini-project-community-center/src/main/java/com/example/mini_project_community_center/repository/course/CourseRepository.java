package com.example.mini_project_community_center.repository.course;

import com.example.mini_project_community_center.common.enums.course.CourseCategory;
import com.example.mini_project_community_center.common.enums.course.CourseLevel;
import com.example.mini_project_community_center.common.enums.course.CourseStatus;
import com.example.mini_project_community_center.entity.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> searchCourses(Long centerId, CourseCategory category, CourseLevel level, CourseStatus status, String kstDateString, String kstDateString1, Integer weekday, LocalTime startTime, LocalTime endTime, String q, Pageable pageable);
}
