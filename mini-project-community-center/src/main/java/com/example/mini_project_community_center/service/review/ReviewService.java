package com.example.mini_project_community_center.service.review;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.review.request.ReviewCreateRequest;
import com.example.mini_project_community_center.dto.review.request.ReviewUpdateRequest;
import com.example.mini_project_community_center.dto.review.response.ReviewDetailResponse;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import jakarta.validation.Valid;

import java.util.List;

public interface ReviewService {
    ResponseDto<ReviewDetailResponse> createReview(UserPrincipal userPrincipal, @Valid ReviewCreateRequest req);
    ResponseDto<ReviewDetailResponse> updateReview(UserPrincipal userPrincipal, Long reviewId, @Valid ReviewUpdateRequest req);
    ResponseDto<List<ReviewDetailResponse>> getCourseReviews(Long courseId, Integer page, Integer size, String sort);
    ResponseDto<List<ReviewDetailResponse>> getMyReviews(UserPrincipal userPrincipal, Integer page, Integer size, String sort);
    ResponseDto<Void> deleteReview(UserPrincipal userPrincipal, Long reviewId);
}
