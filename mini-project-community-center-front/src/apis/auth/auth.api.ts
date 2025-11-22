import type { LoginRequest, LoginResponse, SignupRequest } from "@/types/auth/auth.dto";
import { publicApi } from "../common/axiosInstance";
import type { ApiResponse } from "@/types/common/ApiResponse";
import { AUTH_PATH } from "./auth.path";

export const authApi = {
  studentLogin: async (data: LoginRequest): Promise<LoginResponse> => {
    const res = await publicApi.post<ApiResponse<LoginResponse>>(
      AUTH_PATH.STUDENT_LOGIN,
      data
    );
    return res.data.data;
  },

  teacherLogin: async (data: LoginRequest): Promise<LoginResponse> => {
    const res = await publicApi.post<ApiResponse<LoginResponse>>(
      AUTH_PATH.TEACHER_LOGIN,
      data
    );
    return res.data.data;
  },

  staffLogin: async (data: LoginRequest): Promise<LoginResponse> => {
    const res = await publicApi.post<ApiResponse<LoginResponse>>(
      AUTH_PATH.STAFF_LOGIN,
      data
    );
    return res.data.data;
  },

  studentSignup: async (data: SignupRequest): Promise<void> => {
    const res = await publicApi.post<ApiResponse<void>>(
      AUTH_PATH.STUDENT_SIGNUP,
      data
    );
    return res.data.data;
  },

  teacherSignup: async (data: SignupRequest): Promise<void> => {
    const res = await publicApi.post<ApiResponse<void>>(
      AUTH_PATH.TEACHER_SIGNUP,
      data
    );
    return res.data.data;
  },

  StaffSignup: async (data: SignupRequest): Promise<void> => {
    const res = await publicApi.post<ApiResponse<void>>(
      AUTH_PATH.STAFF_SIGNUP,
      data
    );
    return res.data.data;
  },

  refresh: async (refreshToken: string): Promise<LoginResponse> => {
    const res = await publicApi.post<ApiResponse<LoginResponse>>(
      AUTH_PATH.REFRESH,
      {refreshToken}
    );
    return res.data.data;
  }

};