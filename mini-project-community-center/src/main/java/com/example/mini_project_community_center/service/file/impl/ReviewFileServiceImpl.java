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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewFileServiceImpl implements ReviewFileService {

    private final ReviewRepository reviewRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final FileServiceImpl fileService;

    private final int MAX_ATTACH = 3;

    @Transactional
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

            ReviewFile reviewFile = ReviewFile.of(review, info, order++);
            reviewFileRepository.save(reviewFile);
        }
        return ResponseDto.success("정상적으로 업로드 되었습니다.");
    }

    @Override
    public ResponseDto<List<ReviewFileListResponseDto>> getFilesByReview(Long reviewId) {
        List<ReviewFile> reviewFiles = reviewFileRepository.findByReviewIdOrderByDisplayOrderAsc(reviewId);

        List<ReviewFileListResponseDto> file = reviewFiles.stream()
                .map(ReviewFile::getFileInfo)
                .filter(Objects::nonNull)
                .map(fileInfo -> ReviewFileListResponseDto.fromEntity(fileInfo))
                .toList();

        return ResponseDto.success(file);
    }

    @Transactional
    @Override
    public ResponseDto<Void> deleteReviewFile(Long fileId) {
        ReviewFile reviewFile = reviewFileRepository.findByFileInfoId(fileId)
                .orElseThrow(()-> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 파일이 존재하지 않습니다: " + fileId));

        FileInfo fileInfo = reviewFile.getFileInfo();

        reviewFileRepository.delete(reviewFile);
        fileService.deleteFile(fileInfo);

        return ResponseDto.success("성공적으로 삭제되었습니다.");
    }

    @Transactional
    @Override
    public ResponseDto<Void> updateReviewFiles(Long reviewId, ReviewFileUpdateRequestDto dto) {
        List<Long> keepIds = dto.keepFileIds() == null ? List.of() : dto.keepFileIds();
        List<MultipartFile> newFiles = dto.files();

        List<ReviewFile> currentFiles = reviewFileRepository.findByReviewIdOrderByDisplayOrderAsc(reviewId);

        List<ReviewFile> deleteTargets = currentFiles.stream()
                .filter(reviewFile -> {
                    FileInfo info = reviewFile.getFileInfo();
                    return info == null || !keepIds.contains(info.getId());
                })
                .toList();

        for (ReviewFile file: deleteTargets) {
            reviewFileRepository.delete(file);
            FileInfo info = file.getFileInfo();
            if (info != null) {
                fileService.deleteFile(info);
            }
        }

        if (newFiles != null && !newFiles.isEmpty()) {
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 리뷰가 존재하지 않습니다: " + reviewId));

            int maxOrder = reviewFileRepository.findByReviewIdOrderByDisplayOrderAsc(reviewId)
                    .stream()
                    .mapToInt(ReviewFile::getDisplayOrder)
                    .max()
                    .orElse(-1);

            for (MultipartFile mf: newFiles) {
                FileInfo info = fileService.saveReviewFile(reviewId, mf);
                ReviewFile rf = ReviewFile.of(review, info, maxOrder++);
                reviewFileRepository.save(rf);
            }
        }

        return ResponseDto.success("성공적으로 수정되었습니다.");
    }
}
