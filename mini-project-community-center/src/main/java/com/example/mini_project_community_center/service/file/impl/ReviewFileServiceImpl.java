package com.example.mini_project_community_center.service.file.impl;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.file.request.ReviewFileUpdateRequestDto;
import com.example.mini_project_community_center.dto.file.response.ReviewFileListResponseDto;
import com.example.mini_project_community_center.repository.file.ReviewFileRepository;
import com.example.mini_project_community_center.service.file.ReviewFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewFileServiceImpl implements ReviewFileService {
    private final ReviewFileRepository reviewFileRepository;

    @Override
    public ResponseDto<Void> uploadReviewFiles(Long reviewId, List<MultipartFile> files) {
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
