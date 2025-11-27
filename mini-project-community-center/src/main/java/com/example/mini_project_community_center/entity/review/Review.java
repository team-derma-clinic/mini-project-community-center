package com.example.mini_project_community_center.entity.review;

import com.example.mini_project_community_center.common.enums.review.ReviewStatus;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.entity.course.Course;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "reviews",
        uniqueConstraints = {@UniqueConstraint(name = "uk_review_once", columnNames = {"course_id", "user_id"})},
        indexes = {@Index(name = "idx_review_course", columnList = "course_id, rating")}
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_review_course"), nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_review_user"), nullable = false)
    private User user;

    @Column(name = "rating", nullable = false)
    private Byte rating;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ReviewStatus status = ReviewStatus.DRAFT;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

    public static Review createReview(
            Course course,
            User user,
            Byte rating,
            String content,
            ReviewStatus status,
            LocalDateTime createdAt
    ){
        Review review = new Review();
        review.course = course;
        review.user = user;
        review.rating = rating;
        review.content = content;
        review.status = status != null ? status : ReviewStatus.DRAFT;
        review.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        return review;
    }

    public void updateRating(Byte rating) {
        if (rating != null && rating >= 1 && rating <= 5) {
            this.rating = rating;
        }
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void approve() {
        this.status = ReviewStatus.APPROVED;
    }

    public void reject() {
        this.status = ReviewStatus.REJECTED;
    }

    public void draft() {
        this.status = ReviewStatus.DRAFT;
    }

}
