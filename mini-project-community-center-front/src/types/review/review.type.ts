import type { ReviewRating } from "./review.enum.type";

export interface ReviewFormData {
    courseId: number;
    rating: ReviewRating;
    content?: string;
    files?: File[];
}