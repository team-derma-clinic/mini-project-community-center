import type { PaymentMethod } from "./payment.enum.type";

export interface PaymentCreateForm {
  enrollmentId: number;
  amount: number;
  method: PaymentMethod;
}

export interface PaymentRefundForm {
  reason: string;
}