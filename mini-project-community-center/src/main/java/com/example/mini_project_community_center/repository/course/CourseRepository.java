package com.example.mini_project_community_center.repository.course;

import com.example.mini_project_community_center.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCenterId(Long centerId);
}
