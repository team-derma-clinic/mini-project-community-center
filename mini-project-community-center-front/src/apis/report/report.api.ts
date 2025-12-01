import type {
  CourseReportRequest,
  CourseReportResponse,
} from "@/types/report/report.dto";
import { privateApi } from "../common/axiosInstance";
import type { ApiResponse } from "@/types/common/ApiResponse";
import { REPORT_PATH } from "./report.path";

export const reportApi = {
  // 1. 강좌별 통계(등록/정원/환불)
  getCourseReport: async (
    params?: CourseReportRequest
  ): Promise<CourseReportResponse> => {
    const res = await privateApi.get<ApiResponse<CourseReportResponse>>(
      REPORT_PATH.COURSES,
      { params }
    );
    return res.data.data;
  },

  // 2. 출석 통계(세션/강좌/사용자)

  // 3. 카테고리별 통계(등록수/평균별점/인기순위)

  // 4. 강사별 통계(담당강좌수/평균별점/출석률)
};
