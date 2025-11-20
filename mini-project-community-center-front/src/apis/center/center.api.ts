import type { CenterCreateRequest, CenterDetailResponse, CenterListResponse, CenterUpdateRequest } from "@/types/center/center.dto";
import { privateApi, publicApi } from "../common/axiosInstance";
import { CENTER_PATH } from "./center.path";

export const centerApi = {
  createCenter: async (data: CenterCreateRequest): Promise<CenterDetailResponse> => {
    const res = await privateApi.post(CENTER_PATH.CREATE, data);
    return res.data.data;
  },

  getCenters: async (): Promise<CenterListResponse> => {
    const res = await publicApi.get(CENTER_PATH.LIST);
    return res.data.data;
  },

  getCenterDetail: async(centerId: number): Promise<CenterDetailResponse> => {
    const res = await publicApi.get(CENTER_PATH.DETAIL(centerId));
    return res.data.data;
  },

  updateCenter: async(centerId: number, data: CenterUpdateRequest): Promise<CenterDetailResponse> => {
    const res = await privateApi.put(CENTER_PATH.UPDATE(centerId), data);
    return res.data.data;
  },

  deleteCenter: async(centerId: number): Promise<void> => {
    await privateApi.delete(CENTER_PATH.DELETE(centerId));
  }
}