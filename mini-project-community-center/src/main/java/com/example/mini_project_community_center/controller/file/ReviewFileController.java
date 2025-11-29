package com.example.mini_project_community_center.controller.file;

import com.example.mini_project_community_center.common.apis.ReviewFileApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.file.request.ReviewFileUpdateRequestDto;
import com.example.mini_project_community_center.dto.file.response.ReviewFileListResponseDto;
import com.example.mini_project_community_center.service.file.ReviewFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(ReviewFileApi.ROOT)
@RequiredArgsConstructor
public class ReviewFileController {
    private final ReviewFileService reviewFileService;

    @PostMapping(ReviewFileApi.UPLOAD)
    public ResponseEntity<ResponseDto<Void>> uploadReviewFiles(
            @PathVariable Long reviewId,
            @RequestParam("files")List<MultipartFile> files
    ) {
        ResponseDto<Void> data = reviewFileService.uploadReviewFiles(reviewId, files);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping(ReviewFileApi.LIST)
    public ResponseEntity<ResponseDto<List<ReviewFileListResponseDto>>> getFilesByReview(
            @PathVariable Long reviewId
    ) {
        ResponseDto<List<ReviewFileListResponseDto>> files = reviewFileService.getFilesByReview(reviewId);
        return ResponseEntity.ok(files);
    }

    @DeleteMapping(ReviewFileApi.DELETE)
    public ResponseEntity<ResponseDto<Void>> deleteReviewFile(
            @PathVariable Long fileId
    ){
        ResponseDto<Void> data = reviewFileService.deleteReviewFile(fileId);
        return ResponseEntity.ok(data);
    }

    @PutMapping(
            value = ReviewFileApi.UPDATE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseDto<?>> updateBoardFiles(
            @PathVariable Long reviewId,
            @ModelAttribute ReviewFileUpdateRequestDto dto
    ) {
        ResponseDto<Void> data = reviewFileService.updateReviewFiles(reviewId, dto);
        return ResponseEntity.ok(data);
    }

}
