package com.example.mini_project_community_center.repository.file;

import com.example.mini_project_community_center.entity.review.ReviewFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewFileRepository extends JpaRepository<ReviewFile, Long> {
List<ReviewFile> findByReviewIdOrderByDisplayOrderAsc(Long ReviewId);

Optional<ReviewFile> findByFileInfoId(Long fileId);
}
