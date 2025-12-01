package com.example.mini_project_community_center.service.review.impl;

import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.common.enums.review.ReviewStatus;
import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.review.request.ReviewCreateRequest;
import com.example.mini_project_community_center.dto.review.request.ReviewUpdateRequest;
import com.example.mini_project_community_center.dto.review.response.ReviewDetailResponse;
import com.example.mini_project_community_center.entity.course.Course;
import com.example.mini_project_community_center.entity.review.Review;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.repository.course.course.CourseRepository;
import com.example.mini_project_community_center.repository.review.ReviewRepository;
import com.example.mini_project_community_center.security.UserPrincipal;
import com.example.mini_project_community_center.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;

    @Transactional
    @Override
    public ResponseDto<ReviewDetailResponse> createReview(UserPrincipal userPrincipal, ReviewCreateRequest req) {
        Course course = courseRepository.findById(req.courseId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        User user = userPrincipal.getUser();

        if (reviewRepository.existsByCourseIdAndUserId(req.courseId(), user.getId())) {
            throw new BusinessException(ErrorCode.DB_CONSTRAINT, "이미 리뷰가 존재합니다.");
        }

        Review review = Review.createReview(
                course,
                user,
                req.rating(),
                req.content(),
                ReviewStatus.DRAFT,
                null
        );

        Review saved = reviewRepository.save(review);

        return ResponseDto.success(toDetailResponse(saved));
    }

    @Transactional
    @Override
    public ResponseDto<ReviewDetailResponse> updateReview(UserPrincipal userPrincipal, Long reviewId, ReviewUpdateRequest req) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "리뷰를 찾을 수 없습니다. reviewId: " + reviewId));

        if (!review.getUser().getId().equals(userPrincipal.getUser().getId())) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED, "본인의 리뷰만 수정할 수 있습니다.")
        }

        review.updateRating(req.rating());
        review.updateContent(req.content());

        Review saved = reviewRepository.save(review);

        return ResponseDto.success(toDetailResponse(saved));
    }

    @Override
    public ResponseDto<List<ReviewDetailResponse>> getCourseReviews(Long courseId, Integer page, Integer size, String sort) {
        List<Review> reviews = reviewRepository.findByCourseId(courseId);

        List<ReviewDetailResponse> items = reviews.stream()
                .map(this::toDetailResponse)
                .collect(Collectors.toList());

        return ResponseDto.success(items);
    }

    @Override
    public ResponseDto<List<ReviewDetailResponse>> getMyReviews(UserPrincipal userPrincipal, Integer page, Integer size, String sort) {
        User user = userPrincipal.getUser();

        List<Review> reviews = reviewRepository.findByUserOrderByCreatedAtDesc(user);

        List<ReviewDetailResponse> items = reviews.stream()
                .map(this::toDetailResponse)
                .collect(Collectors.toList());

        return ResponseDto.success(items);
    }

    @Transactional
    @Override
    public ResponseDto<Void> deleteReview(UserPrincipal userPrincipal, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "리뷰를 찾을 수 없습니다. reviewId: " + reviewId));

        if (!review.getUser().getId().equals(userPrincipal.getUser().getId())) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED, "본인의 리뷰만 삭제할 수 있습니다.");
        }

        reviewRepository.delete(review);

        return ResponseDto.success("리뷰가 삭제되었습니다.");
    }

    private ReviewDetailResponse toDetailResponse(Review review) {
        return new ReviewDetailResponse(
                review.getId(),
                review.getCourse().getId(),
                review.getCourse().getTitle(),
                review.getUser().getId(),
                review.getUser().getName(),
                review.getRating(),
                review.getContent(),
                review.getStatus(),
                DateUtils.toKstString(review.getCreatedAt())
        );
    }
}
