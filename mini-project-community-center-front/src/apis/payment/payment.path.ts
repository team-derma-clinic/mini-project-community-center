import { BASE } from "../common/base.path";

const PAYMENT_PREFIX = `${BASE}/payments`;

export const PAYMENT_PATH = {
  ROOT: PAYMENT_PREFIX,
  
  CREATE: PAYMENT_PREFIX,
  LIST: PAYMENT_PREFIX,

  BY_PAYMENT_ID: (paymentId: number) => `${PAYMENT_PREFIX}/${paymentId}`,
  REFUND: (paymentId: number) => `${PAYMENT_PREFIX}/${paymentId}/refund`,
}