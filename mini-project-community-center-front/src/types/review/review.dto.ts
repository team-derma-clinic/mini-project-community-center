import type { ReviewRating, ReviewStatus } from "./review.enum.type";

export interface CreateReviewRequest {
  courseId: number;
  rating: ReviewRating;
  content?: string;
}

export interface DeleteReviewRequest {
  reason?: string;
}

export interface SearchCourseReview {
  page?: number;
  size?: number;
  sort?: string;
}

export interface SearchMyReview {
  page?: number;
  size?: number;
  sort?: string;
}

export interface ReviewListItemResponse {
  id: number;
  courseId: number;
  courseName?: string;
  userId: number;
  userName?: string;
  rating: ReviewRating;
  content?: string;
  status: ReviewStatus;
  createdAt: string;
}

export type ReviewListResponse = ReviewListItemResponse[];

export interface ReviewDetailResponse {
  id: number;
  courseId: number;
  courseName?: string;
  userId: number;
  userName?: string;
  rating: ReviewRating;
  content?: string;
  status: ReviewStatus;
  createdAt: string;
}
