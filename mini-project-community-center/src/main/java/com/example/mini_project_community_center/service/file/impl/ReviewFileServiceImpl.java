package com.example.mini_project_community_center.service.file.impl;

import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.file.request.ReviewFileUpdateRequestDto;
import com.example.mini_project_community_center.dto.file.response.ReviewFileListResponseDto;
import com.example.mini_project_community_center.entity.file.FileInfo;
import com.example.mini_project_community_center.entity.review.Review;
import com.example.mini_project_community_center.entity.review.ReviewFile;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.repository.file.ReviewFileRepository;
import com.example.mini_project_community_center.repository.review.ReviewRepository;
import com.example.mini_project_community_center.service.file.ReviewFileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewFileServiceImpl implements ReviewFileService {

    private final ReviewRepository reviewRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final FileServiceImpl fileService;

    private final int MAX_ATTACH = 3;

    @Override
    public ResponseDto<Void> uploadReviewFiles(Long reviewId, List<MultipartFile> files) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 리뷰를 찾을 수 없습니다: " + reviewId));

        if (files.size() > MAX_ATTACH) {
            throw new IllegalArgumentException("최대 " + MAX_ATTACH + "개까지 업로드 가능합니다.");
        }

        int order = 0;

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                continue;
            }
            FileInfo info = fileService.saveReviewFile(reviewId, file);
            if (info == null) continue;

            ReviewFile reviewFile = new ReviewFile(review, info, order++);
            reviewFileRepository.save(reviewFile);
        }
        return null;
    }

    @Override
    public ResponseDto<List<ReviewFileListResponseDto>> getFilesByReview(Long reviewId) {
        return null;
    }

    @Override
    public ResponseDto<Void> deleteReviewFile(Long fileId) {
        return null;
    }

    @Override
    public ResponseDto<Void> updateReviewFiles(Long reviewId, ReviewFileUpdateRequestDto dto) {
        return null;
    }
}
