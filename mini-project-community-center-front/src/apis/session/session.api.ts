import type { SessionCreateRequest, SessionDetailResponse, SessionListResponse, SessionSearchParams, SessionStatusUpdateRequest, SessionUpdateRequest } from "@/types/session/session.dto"
import { privateApi, publicApi } from "../common/axiosInstance"
import { COURSE_SESSION_PATH } from "./course.session.path"
import { SESSION_PATH } from "./session.path";
import type { ApiResponse } from "@/types/common/ApiResponse";

export const sessionApi = {
  createSession: async (courseId: number, data: SessionCreateRequest): Promise<SessionDetailResponse> => {
    const res = await privateApi.post<ApiResponse<SessionDetailResponse>>(COURSE_SESSION_PATH.CREATE(courseId), data);
    return res.data.data;
  },

  searchSessions: async (courseId: number, params?: SessionSearchParams): Promise<SessionListResponse> => {
    const res = await publicApi.get<ApiResponse<SessionListResponse>>(COURSE_SESSION_PATH.LIST(courseId), { params });
    return res.data.data;
  },

  getSessionDetail: async (sessionId: number): Promise<SessionDetailResponse> => {
    const res = await publicApi.get<ApiResponse<SessionDetailResponse>>(SESSION_PATH.DETAIL(sessionId));
    return res.data.data;
  },

  updateSession: async (sessionId: number, data: SessionUpdateRequest): Promise<SessionDetailResponse> => {
    const res = await privateApi.put<ApiResponse<SessionDetailResponse>>(SESSION_PATH.UPDATE(sessionId), data);
    return res.data.data;
  },

  updateSesisonStatus: async (sessionId: number, data: SessionStatusUpdateRequest): Promise<SessionDetailResponse> => {
    const res = await privateApi.put<ApiResponse<SessionDetailResponse>>(SESSION_PATH.STATUS(sessionId), data);
    return res.data.data;
  },

  deleteSession: async (sessionId: number, hardDelete: boolean = false): Promise<void> => {
    await privateApi.delete<ApiResponse<void>>(SESSION_PATH.BY_SESSION_ID(sessionId), { params: hardDelete });
  }
}