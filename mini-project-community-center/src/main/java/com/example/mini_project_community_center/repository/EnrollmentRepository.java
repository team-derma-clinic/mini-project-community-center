package com.example.mini_project_community_center.repository;

import com.example.mini_project_community_center.entity.Enrollment;
import com.example.mini_project_community_center.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUserOrderByEnrolledAtDesc(User user);
}
