import type { CenterCreateRequest, CenterDetailResponse, CenterListResponse, CenterSearchParams, CenterUpdateRequest } from "@/types/center/center.dto";
import { privateApi, publicApi } from "../common/axiosInstance";
import { CENTER_PATH } from "./center.path";
import type { ApiResponse } from "@/types/common/ApiResponse";

export const centerApi = {
  createCenter: async (data: CenterCreateRequest): Promise<CenterDetailResponse> => {
    const res = await privateApi.post<ApiResponse<CenterDetailResponse>>(CENTER_PATH.CREATE, data);
    return res.data.data;
  },

  searchCenters: async (params?: CenterSearchParams): Promise<CenterListResponse> => {
    const res = await publicApi.get<ApiResponse<CenterListResponse>>(CENTER_PATH.LIST, { params });
    return res.data.data;
  },

  getCenterDetail: async(centerId: number): Promise<CenterDetailResponse> => {
    const res = await publicApi.get<ApiResponse<CenterDetailResponse>>(CENTER_PATH.DETAIL(centerId));
    return res.data.data;
  },

  updateCenter: async(centerId: number, data: CenterUpdateRequest): Promise<CenterDetailResponse> => {
    const res = await privateApi.put<ApiResponse<CenterDetailResponse>>(CENTER_PATH.UPDATE(centerId), data);
    return res.data.data;
  },

  deleteCenter: async(centerId: number, hardDelete: boolean = false): Promise<void> => {
    await privateApi.delete<ApiResponse<void>>(CENTER_PATH.BY_CENTER_ID(centerId), {
      params: { hardDelete }
    });
  }
}