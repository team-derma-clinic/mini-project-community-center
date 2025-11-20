import type {
  AttendanceDetailResponse,
  AttendanceListResponse,
  CreateAttendanceRequest,
  SearchCourseAttendance,
  SearchSessionAttendance,
  UpdateAttendanceRequest,
} from "@/types/attendance/attendance.dto";
import { privateApi } from "../common/axiosInstance";
import type { ApiResponse } from "@/types/common/ApiResponse";
import { ATTENDANCE_PATH } from "./attendance.path";

export const attendanceApi = {
  upsertAttendance: async (
    data: CreateAttendanceRequest
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
    data: UpdateAttendanceRequest
  ): Promise<AttendanceDetailResponse> => {
    const res = await privateApi.post<ApiResponse<AttendanceDetailResponse>>(
      ATTENDANCE_PATH.UPDATE(attendanceId),
      data
    );
    return res.data.data;
  },

  deleteAttendance: async (attendanceId: number): Promise<void> => {
    await privateApi.delete<ApiResponse<void>>(
      ATTENDANCE_PATH.DETAIL(attendanceId)
    );
  },

  getCourseAttendance: async (
    courseId: number,
    params?: SearchCourseAttendance
  ): Promise<AttendanceListResponse> => {
    const res = await privateApi.get<ApiResponse<AttendanceListResponse>>(
      ATTENDANCE_PATH.COURSE_LIST(courseId),
      { params }
    );
    return res.data.data;
  },

  getSessionAttendance: async (
    sessionId: number,
    params?: SearchSessionAttendance
  ): Promise<AttendanceListResponse> => {
    const res = await privateApi.get<ApiResponse<AttendanceListResponse>>(
      ATTENDANCE_PATH.SESSION_LIST(sessionId),
      { params }
    );
    return res.data.data;
  },
};
