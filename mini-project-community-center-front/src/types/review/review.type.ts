import type { ReviewRating } from "./review.enum.type";

export interface CreateReviewForm {
    courseId: number;
    rating: ReviewRating;
    content?: string;
}

export interface DeleteReviewForm {
    reason?: string;
}