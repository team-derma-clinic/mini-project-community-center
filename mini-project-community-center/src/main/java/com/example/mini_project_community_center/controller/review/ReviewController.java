package com.example.mini_project_community_center.controller.review;

import com.example.mini_project_community_center.common.apis.ReviewApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.review.request.ReviewCreateRequest;
import com.example.mini_project_community_center.dto.review.request.ReviewUpdateRequest;
import com.example.mini_project_community_center.dto.review.response.ReviewDetailResponse;
import com.example.mini_project_community_center.dto.review.response.ReviewListItemResponse;
import com.example.mini_project_community_center.service.review.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ReviewApi.ROOT)
public class ReviewController {
private final ReviewService reviewService;

// POST /api/v1/reviews
    @PostMapping
    public ResponseEntity<ResponseDto<ReviewDetailResponse>> createReview(
            @Valid @RequestBody ReviewCreateRequest req
            ) {
        ResponseDto<ReviewDetailResponse> data = reviewService.createReview(req);
        return ResponseEntity.ok(data);
    }

    // PUT /api/v1/reviews/{reviewId}
    @PutMapping(ReviewApi.ID_ONLY)
    public ResponseEntity<ResponseDto<ReviewDetailResponse>> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewUpdateRequest req
            ) {
        ResponseDto<ReviewDetailResponse> data = reviewService.updateReview(reviewId, req);
        return ResponseEntity.ok(data);
    }

    // GET /api/v1/courses/{courseId}/reviews
    @GetMapping(ReviewApi.COURSE_REVIEWS)
    public ResponseEntity<ResponseDto<List<ReviewListItemResponse>>> getCourseReviews(
            @PathVariable Long courseId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort
    ) {
        ResponseDto<List<ReviewListItemResponse>> data = reviewService.getCourseReviews(courseId, page, size, sort);
        return ResponseEntity.ok(data);
    }

    // GET /api/v1/reviews/me
    @GetMapping(ReviewApi.MY_REVIEWS)
    public ResponseEntity<ResponseDto<List<ReviewListItemResponse>>> getMyReviews(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort
    ) {
        ResponseDto<List<ReviewListItemResponse>> data = reviewService.getMyReviews(page, size, sort);
        return ResponseEntity.ok(data);
    }

    // DELETE /api/v1/reviews/{reviewId}
    @DeleteMapping(ReviewApi.ID_ONLY)
    public ResponseEntity<ResponseDto<Void>> deleteReview(
            @PathVariable Long reviewId
    ) {
    ResponseDto<Void> data = reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(data);
    }
}
