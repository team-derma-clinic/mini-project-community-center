package com.example.mini_project_community_center.repository.file;

import com.example.mini_project_community_center.entity.course.CourseFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseFileRepository extends JpaRepository<CourseFile, Long> {
List<CourseFile> findByCourseIdOrderByDisplayOrderAsc(Long courseId);

Optional<CourseFile> findByFileInfoId(Long fileId);
}
