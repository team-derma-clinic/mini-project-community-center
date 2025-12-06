package com.example.mini_project_community_center.controller.review;

import com.example.mini_project_community_center.common.apis.ReviewApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.review.request.ReviewCreateRequest;
import com.example.mini_project_community_center.dto.review.request.ReviewUpdateRequest;
import com.example.mini_project_community_center.dto.review.response.ReviewDetailResponse;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.review.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ReviewApi.ROOT)
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // POST /api/v1/reviews
    @PostMapping
    public ResponseEntity<ResponseDto<ReviewDetailResponse>> createReview(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody ReviewCreateRequest req
    ) {
        ResponseDto<ReviewDetailResponse> data = reviewService.createReview(userPrincipal, req);
        return ResponseEntity.ok(data);
    }

    // PUT /api/v1/reviews/{reviewId}
    @PutMapping(ReviewApi.ID_ONLY)
    public ResponseEntity<ResponseDto<ReviewDetailResponse>> updateReview(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewUpdateRequest req
    ) {
        ResponseDto<ReviewDetailResponse> data = reviewService.updateReview(userPrincipal, reviewId, req);
        return ResponseEntity.ok(data);
    }

    // GET /api/v1/courses/{courseId}/reviews
    @GetMapping(ReviewApi.COURSE_REVIEWS)
    public ResponseEntity<ResponseDto<List<ReviewDetailResponse>>> getCourseReviews(
            @PathVariable Long courseId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort
    ) {
        ResponseDto<List<ReviewDetailResponse>> data = reviewService.getCourseReviews(courseId, page, size, sort);
        return ResponseEntity.ok(data);
    }

    // GET /api/v1/reviews/me
    @GetMapping(ReviewApi.MY_REVIEWS)
    public ResponseEntity<ResponseDto<List<ReviewDetailResponse>>> getMyReviews(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort
    ) {
        ResponseDto<List<ReviewDetailResponse>> data = reviewService.getMyReviews(userPrincipal, page, size, sort);
        return ResponseEntity.ok(data);
    }

    // DELETE /api/v1/reviews/{reviewId}
    @DeleteMapping(ReviewApi.ID_ONLY)
    public ResponseEntity<ResponseDto<Void>> deleteReview(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long reviewId
    ) {
        ResponseDto<Void> data = reviewService.deleteReview(userPrincipal, reviewId);
        return ResponseEntity.ok(data);
    }
}
