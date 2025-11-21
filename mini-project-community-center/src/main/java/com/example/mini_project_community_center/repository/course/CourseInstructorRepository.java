package com.example.mini_project_community_center.repository.course;

import com.example.mini_project_community_center.entity.CourseInstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseInstructorRepository extends JpaRepository<CourseInstructor, Long> {
}
