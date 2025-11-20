import type { SessionCreateRequest, SessionDetailResponse, SessionListResponse, SessionStatusUpdateRequest, SessionUpdateRequest } from "@/types/session/session.dto"
import { privateApi, publicApi } from "../common/axiosInstance"
import { COURSE_SESSION_PATH } from "./course.session.path"
import { SESSION_PATH } from "./session.path";

export const sessionApi = {
  createSession: async (courseId: number, data: SessionCreateRequest): Promise<SessionDetailResponse> => {
    const res = await privateApi.post(COURSE_SESSION_PATH.CREATE(courseId), data);
    return res.data.data;
  },

  getSessions: async (courseId: number): Promise<SessionListResponse> => {
    const res = await publicApi.get(COURSE_SESSION_PATH.LIST(courseId));
    return res.data.data;
  },

  getSessionDetail: async (sessionId: number): Promise<SessionDetailResponse> => {
    const res = await publicApi.get(SESSION_PATH.DETAIL(sessionId));
    return res.data.data;
  },

  updateSession: async (sessionId: number, data: SessionUpdateRequest): Promise<SessionDetailResponse> => {
    const res = await privateApi.put(SESSION_PATH.UPDATE(sessionId), data);
    return res.data.data;
  },

  updateSesisonStatus: async (sessionId: number, data: SessionStatusUpdateRequest): Promise<SessionDetailResponse> => {
    const res = await privateApi.put(SESSION_PATH.STATUS(sessionId), data);
    return res.data.data;
  },

  deleteSession: async (sessionId: number): Promise<void> => {
    await privateApi.delete(SESSION_PATH.BY_SESSION_ID(sessionId));
  }
}