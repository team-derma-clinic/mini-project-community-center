package com.example.mini_project_community_center.repository.review;

import com.example.mini_project_community_center.entity.Review;
import com.example.mini_project_community_center.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByCourseIdAndUserId(Long courseId, Long userId);

    List<Review> findByCourseId(Long courseId);

    List<Review> findByUserOrderByCreatedAtDesc(User user);

    boolean existsByCourseIdAndUserId(Long courseId, Long userId);
}
