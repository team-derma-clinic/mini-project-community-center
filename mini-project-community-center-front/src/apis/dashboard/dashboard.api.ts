import type {
  InstructorDashboardResponse,
  SearchInstructorDashboardRequest,
  SearchStaffDashboardRequest,
  StaffDashboardResponse,
} from "@/types/dashboard/dashboard.dto";
import { privateApi } from "../common/axiosInstance";
import type { ApiResponse } from "@/types/common/ApiResponse";
import { DASHBOARD_PATH } from "./dashboard.path";

export const dashboardApi = {
  getInstructorDashboard: async (
    params?: SearchInstructorDashboardRequest
  ): Promise<InstructorDashboardResponse> => {
    const res = await privateApi.get<ApiResponse<InstructorDashboardResponse>>(
      DASHBOARD_PATH.INSTRUCTOR_ME,
      { params }
    );
    return res.data.data;
  },

  getStaffDashboard: async (
    params?: SearchStaffDashboardRequest
  ): Promise<StaffDashboardResponse> => {
    const res = await privateApi.get<ApiResponse<StaffDashboardResponse>>(
      DASHBOARD_PATH.STAFF,
      { params }
    );
    return res.data.data;
  },
};
