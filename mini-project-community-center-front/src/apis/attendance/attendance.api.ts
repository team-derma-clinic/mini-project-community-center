import type { AttendanceCreateRequest, AttendanceDetailResponse, AttendanceListResponse, AttendanceSearchParams, AttendanceUpdateRequest } from "@/types/attendance/attendance.dto";
import { privateApi } from "../common/axiosInstance";
import type { ApiResponse } from "@/types/common/ApiResponse";
import { ATTENDANCE_PATH } from "./attendance.path";

export const attendanceApi = {
  upsertAttendance: async (
    data: AttendanceCreateRequest
  ): Promise<AttendanceDetailResponse> => {
    const res = await privateApi.post<ApiResponse<AttendanceDetailResponse>>(
      ATTENDANCE_PATH.UPSERT,
      data
    );
    return res.data.data;
  },

  getAttendanceDetail: async (
    attendanceId: number
  ): Promise<AttendanceDetailResponse> => {
    const res = await privateApi.get<ApiResponse<AttendanceDetailResponse>>(
      ATTENDANCE_PATH.DETAIL(attendanceId)
    );
    return res.data.data;
  },

  updateAttendance: async (
    attendanceId: number,
    data: AttendanceUpdateRequest
  ): Promise<AttendanceDetailResponse> => {
    const res = await privateApi.put<ApiResponse<AttendanceDetailResponse>>(
      ATTENDANCE_PATH.UPDATE(attendanceId),
      data
    );
    return res.data.data;
  },

  getCourseAttendance: async (
    courseId: number,
    params?: AttendanceSearchParams
  ): Promise<AttendanceListResponse> => {
    const res = await privateApi.get<ApiResponse<AttendanceListResponse>>(
      ATTENDANCE_PATH.COURSE_LIST(courseId),
      { params }
    );
    return res.data.data;
  },

  getSessionAttendance: async (
    sessionId: number,
    params?: AttendanceSearchParams
  ): Promise<AttendanceListResponse> => {
    const res = await privateApi.get<ApiResponse<AttendanceListResponse>>(
      ATTENDANCE_PATH.SESSION_LIST(sessionId),
      { params }
    );
    return res.data.data;
  },
};
