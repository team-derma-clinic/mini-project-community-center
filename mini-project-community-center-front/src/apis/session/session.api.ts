import type { SessionCreateRequest, SessionStatusUpdateRequest, SessionUpdateRequest } from "@/types/session/session.dto"
import { privateApi, publicApi } from "../common/axiosInstance"
import { COURSE_SESSION_PATH } from "./course.session.path"
import { SESSION_PATH } from "./session.path";

export const sessionApi = {
  createSession: async (courseId: number, data: SessionCreateRequest) => {
    const res = await privateApi.post(COURSE_SESSION_PATH.CREATE(courseId), data);
    return res.data.data;
  },

  getSessions: async (courseId: number) => {
    const res = await publicApi.get(COURSE_SESSION_PATH.LIST(courseId));
    return res.data.data;
  },

  getSessionDetail: async (sessionId: number) => {
    const res = await publicApi.get(SESSION_PATH.DETAIL(sessionId));
    return res.data.data;
  },

  updateSession: async (sessionId: number, data: SessionUpdateRequest) => {
    const res = await privateApi.put(SESSION_PATH.UPDATE(sessionId), data);
    return res.data.data;
  },

  updateSesisonStatus: async (sessionId: number, data: SessionStatusUpdateRequest) => {
    const res = await privateApi.put(SESSION_PATH.STATUS(sessionId), data);
    return res.data.data;
  },
  
  deleteSession: async (sessionId: number) => {
    const res = await privateApi.delete(SESSION_PATH.DELETE(sessionId));
    return res.data.data;
  }
}