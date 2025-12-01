import { privateApi, publicApi } from "../common/axiosInstance";
import type { ApiResponse } from "@/types/common/ApiResponse";
import { REVIEW_PATH } from "./review.path";
import type { ReviewCreateRequest, ReviewDetailResponse, ReviewListParams, ReviewListResponse, ReviewSearchParams, ReviewUpdateRequest } from "@/types/review/review.dto";

export const reviewApi = {
  createReview: async (
    data: ReviewCreateRequest
  ): Promise<ReviewDetailResponse> => {
    const res = await privateApi.post<ApiResponse<ReviewDetailResponse>>(
      REVIEW_PATH.CREATE,
      data
    );
    return res.data.data;
  },
  
    updateReview: async(reviewId: number, data: ReviewUpdateRequest): Promise<ReviewDetailResponse> => {
      const res = await privateApi.put<ApiResponse<ReviewDetailResponse>>(REVIEW_PATH.UPDATE(reviewId), data);
      return res.data.data;
    },

  deleteReview: async (
    reviewId: number
  ): Promise<void> => {
    await privateApi.delete<ApiResponse<void>>(REVIEW_PATH.DELETE(reviewId));
  },

  getMyReviews: async (params?: ReviewSearchParams): Promise<ReviewListResponse> => {
    const res = await privateApi.get<ApiResponse<ReviewListResponse>>(
      REVIEW_PATH.MY_REVIEWS,
      { params }
    );
    return res.data.data;
  },

  getCourseReviews: async (
    courseId: number, params?: ReviewSearchParams
  ): Promise<ReviewListResponse> => {
    const res = await publicApi.get<ApiResponse<ReviewListResponse>>(
      REVIEW_PATH.COURSE_REVIEWS(courseId),
      { params }
    );
    return res.data.data;
  },
};
