import type { ReviewFileListResponse } from "@/types/review/review.file.dto";
import { publicApi } from "../common/axiosInstance";
import { REVIEW_FILE_PATH } from "./review.file.path";

export const reviewApi = {
  uploadReviewFiles: async(reviewId: number, formData: FormData) => {
    const res = await publicApi.post<void> (
      REVIEW_FILE_PATH.POST(reviewId),
      formData,
      { headers: {"Content-Type": "multipart/form-data"}}
    );
    return res.data;
  },

  getFilesByReview: async(reviewId: number) => {
    const res = await publicApi.get<ReviewFileListResponse>(
      REVIEW_FILE_PATH.LIST(reviewId)
    );
    console.log("data: ", res.data);
    return res.data;
  },

  updateBoardFiles: async(fileId: number, formData: FormData) => {
    const res = await publicApi.put<void>(
      REVIEW_FILE_PATH.UPDATE(fileId),
      formData,
      { headers: {"Content-Type": "multipart/form-data"}}
    );
    return res.data;
  },

  deleteBoardFile: async(fileId: number):Promise<void> => {
    await publicApi.delete(REVIEW_FILE_PATH.DELETE(fileId));
  },

}