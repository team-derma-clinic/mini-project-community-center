package com.example.mini_project_community_center.repository.course.instructor;

import com.example.mini_project_community_center.entity.course.CourseInstructor;
import com.example.mini_project_community_center.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseInstructorRepository extends JpaRepository<CourseInstructor, Long> {
    List<User> findAllByCourseId(Long courseId);
}
