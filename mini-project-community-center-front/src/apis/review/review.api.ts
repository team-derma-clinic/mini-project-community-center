import type {
  CreateReviewRequest,
  DeleteReviewRequest,
  ReviewDetailResponse,
  ReviewListResponse,
  SearchCourseReview,
  SearchMyReview,
  UpdateReviewRequest,
} from "@/types/review/review.dto";
import { privateApi, publicApi } from "../common/axiosInstance";
import type { ApiResponse } from "@/types/common/ApiResponse";
import { REVIEW_PATH } from "./review.path";

export const reviewApi = {
  createReview: async (
    data: CreateReviewRequest
  ): Promise<ReviewDetailResponse> => {
    const res = await privateApi.post<ApiResponse<ReviewDetailResponse>>(
      REVIEW_PATH.CREATE,
      data
    );
    return res.data.data;
  },
  
    updateReview: async(reviewId: number, data: UpdateReviewRequest): Promise<ReviewDetailResponse> => {
      const res = await privateApi.put<ApiResponse<ReviewDetailResponse>>(REVIEW_PATH.UPDATE(reviewId), data);
      return res.data.data;
    },

  getCourseReviews: async (
    courseId: number,
    params?: SearchCourseReview
  ): Promise<ReviewListResponse> => {
    const res = await publicApi.get<ApiResponse<ReviewListResponse>>(
      REVIEW_PATH.COURSE_REVIEWS(courseId),
      { params }
    );
    return res.data.data;
  },

  getMyReviews: async (params?: SearchMyReview): Promise<ReviewListResponse> => {
    const res = await privateApi.get<ApiResponse<ReviewListResponse>>(
      REVIEW_PATH.MY_REVIEWS,
      { params }
    );
    return res.data.data;
  },

  deleteReview: async (
    reviewId: number,
    data?: DeleteReviewRequest
  ): Promise<void> => {
    await privateApi.delete<ApiResponse<void>>(REVIEW_PATH.DELETE(reviewId), {
      data,
    });
  },
};
