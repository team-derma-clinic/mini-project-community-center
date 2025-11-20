import type { PaymentMethod, PaymentStatus } from "./payment.enum.type";

export interface PaymentCreateRequest {
  enrollmentId: number;
  amount: number;
  method: PaymentMethod;
}

export interface PaymentRefundRequest {
  reason?: string;
}

export interface PaymentDetailResponse {
  paymentId: number;
  enrollmentId: number;
  amount: number;
  method: PaymentMethod;
  status: PaymentStatus;
  createdAt: string;
  updatedAt?: string;
  refundedAt?: string;
  refundReason?: string;
}

export type PaymentStatusFilter = PaymentStatus | 'ALL';
export type PaymentListResponse = PaymentDetailResponse[];