import type { PaymentMethod, PaymentRefundOption } from "./payment.enum.type";

export interface PaymentCreateForm {
  enrollmentId: number;
  amount: number;
  method: PaymentMethod;
}

export interface PaymentRefundForm {
  reasonOption: PaymentRefundOption;
  otherReason?: string;
}