package com.example.mini_project_community_center.service.file;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.file.request.ReviewFileUpdateRequestDto;
import com.example.mini_project_community_center.dto.file.response.ReviewFileListResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewFileService {
    ResponseDto<Void> uploadReviewFiles(Long reviewId, List<MultipartFile> files);

    ResponseDto<List<ReviewFileListResponseDto>> getFilesByReview(Long reviewId);

    ResponseDto<Void> deleteReviewFile(Long fileId);

    ResponseDto<Void> updateReviewFiles(Long reviewId, ReviewFileUpdateRequestDto dto);
}
