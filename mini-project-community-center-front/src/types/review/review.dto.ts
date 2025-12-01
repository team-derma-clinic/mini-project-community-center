import type { ReviewRating, ReviewStatus } from "./review.enum.type";

export interface ReviewCreateRequest {
  courseId?: number;
  rating?: ReviewRating;
  content?: string;
}

export interface ReviewUpdateRequest {
  rating: ReviewRating;
  content?: string;
}

export interface ReviewSearchParams {
  page?: number;
  size?: number;
  courseId: number;
  status?: ReviewStatus;
  sort?: string;
}

export interface ReviewDetailResponse {
  id: number;
  courseId: number;
  courseName: string;
  userId: number;
  userName: string;
  rating: ReviewRating;
  content: string;
  status: ReviewStatus;
  createAt: string;
}

export interface ReviewListItemResponse {
  id: number;
  courseId: number;
  courseName: string;
  userId: number;
  userName: string;
  rating: ReviewRating;
  content: string;
  status: ReviewStatus;
  createAt: string;
}

export type ReviewListResponse = ReviewListItemResponse[];