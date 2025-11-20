import type { EnrollmentCreateRequest, EnrollmentDetailResponse, EnrollmentListResponse } from "@/types/enrollment/enrollment.dto";
import { privateApi } from "../common/axiosInstance";
import { ENROLLMENT_PATH } from "./enrollment.path";
import type { ApiResponse } from "@/types/common/ApiResponse";


export const enrollmentApi = {
  createEnrollment: async (data: EnrollmentCreateRequest): Promise<EnrollmentDetailResponse> => {
    const res = await privateApi.post<ApiResponse<EnrollmentDetailResponse>>(ENROLLMENT_PATH.CREATE, data);
    return res.data.data;
  },

  getMyEnrollment: async (): Promise<EnrollmentListResponse> => {
    const res = await privateApi.get<ApiResponse<EnrollmentListResponse>>(ENROLLMENT_PATH.ME);
    return res.data.data;
  },

  getEnrollment: async (enrollmentId: number): Promise<EnrollmentDetailResponse> => {
    const res = await privateApi.get<ApiResponse<EnrollmentDetailResponse>>(ENROLLMENT_PATH.DETAIL(enrollmentId))
    return res.data.data;
  },

  cancelEnrollment: async (
    enrollmentId: number,
  ): Promise<EnrollmentDetailResponse> => {
    const res = await privateApi.put<ApiResponse<EnrollmentDetailResponse>>(ENROLLMENT_PATH.CANCEL(enrollmentId))
    return res.data.data;
  }
}