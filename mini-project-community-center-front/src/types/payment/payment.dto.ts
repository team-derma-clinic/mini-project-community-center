import type { PaymentMethod, PaymentStatus } from "./payment.enum.type";

export interface PaymentCreateRequest {
  enrollmentId: number;
  amount: number;
  currency: string;
  method: PaymentMethod;
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