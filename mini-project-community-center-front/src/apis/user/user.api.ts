import type { ApiResponse } from "@/types/common/ApiResponse";
import type { UserDetailResponse, UserListResponse, UserUpdateRequest } from "@/types/user/user.dto";
import { privateApi } from "../common/axiosInstance";
import { USER_PATH } from "./user.path";
import type { RoleType } from "@/types/role/role.enum.type";

export const userApi = {
  getMe: async(): Promise<UserDetailResponse> => {
    const res = await privateApi.get<ApiResponse<UserDetailResponse>>(
      USER_PATH.ME
    );
    return res.data.data;
  },

  updateMe: async(data: UserUpdateRequest): Promise<UserDetailResponse> => {
    const res = await privateApi.put<ApiResponse<UserDetailResponse>>(
      USER_PATH.UPDATE,
      data
    );
    return res.data.data;
  },

  getUserById: async (userId: number): Promise<UserDetailResponse> => {
    const res = await privateApi.get<ApiResponse<UserDetailResponse>>(
      USER_PATH.BY_ID(userId)
    );
    return res.data.data;
  },

  getUserList: async(): Promise<UserListResponse> => {
    const res = await privateApi.get<ApiResponse<UserListResponse>>(
      USER_PATH.LIST
    );
    return res.data.data;
  },

  getUserRole: async(userId: number): Promise<RoleType> => {
    const res = await privateApi.get<ApiResponse<RoleType>>(
      USER_PATH.ROLES(userId)
    );
    return res.data.data;
  }
};