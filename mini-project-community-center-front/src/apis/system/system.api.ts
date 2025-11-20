import type {
  SystemHealthResponse,
  SystemInfoResponse,
} from "@/types/system/system.dto";
import { publicApi } from "../common/axiosInstance";
import { ApiResponse } from "@/types/common/ApiResponse";
import { SYSTEM_PATH } from "./system.path";

export const systemApi = {
  getHealth: async (): Promise<SystemHealthResponse> => {
    const res = await publicApi.get<ApiResponse<SystemHealthResponse>>(
      SYSTEM_PATH.HEALTH
    );
    return res.data.data;
  },

  getInfo: async (): Promise<SystemInfoResponse> => {
    const res = await publicApi.get<ApiResponse<SystemInfoResponse>>(
      SYSTEM_PATH.INFO
    );
    return res.data.data;
  },
};
