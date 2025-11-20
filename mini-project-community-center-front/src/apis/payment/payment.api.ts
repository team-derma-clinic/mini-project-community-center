import type { ApiResponse } from "@/types/common/ApiResponse";
import type { PaymentCreateRequest, PaymentDetailResponse, PaymentListResponse, PaymentRefundRequest } from "@/types/payment/payment.dto";
import { privateApi } from "../common/axiosInstance";
import { PAYMENT_PATH } from "./payment.path";

export const paymentApi = {
  createPayment: async (data: PaymentCreateRequest): Promise<PaymentDetailResponse> => {
    const res = await privateApi.post<ApiResponse<PaymentDetailResponse>>(PAYMENT_PATH.CREATE, data);
    return res.data.data
  },

  getPayments: async (): Promise<PaymentListResponse> => {
    const res = await privateApi.get<ApiResponse<PaymentListResponse>>(PAYMENT_PATH.LIST);
    return res.data.data;
  },

  getPayment: async (paymentId: number): Promise<PaymentDetailResponse> => {
    const res = await privateApi.get<ApiResponse<PaymentDetailResponse>>(PAYMENT_PATH.BY_PAYMENT_ID(paymentId))
    return res.data.data;
  },

  RefundPayment: async (
    paymentId: number,
    data: PaymentRefundRequest
  ): Promise<PaymentDetailResponse> => {
    const res = await privateApi.put<ApiResponse<PaymentDetailResponse>>(PAYMENT_PATH.REFUND(paymentId), data)
    return res.data.data;
  }
}