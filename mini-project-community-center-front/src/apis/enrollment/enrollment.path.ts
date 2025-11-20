import { BASE } from "../common/base.path";

const ENROLLMENT_PREFIX = `${BASE}/enrollments`;

export const ENROLLMENT_PATH = {
  ROOT: ENROLLMENT_PREFIX,
  
  LIST: ENROLLMENT_PREFIX,
  CREATE: ENROLLMENT_PREFIX,

  ME: `${ENROLLMENT_PREFIX}/me`,

  DETAIL: (enrollmentId: number) => `${ENROLLMENT_PREFIX}/${enrollmentId}`,
  REFUND: (enrollmentId: number) => `${ENROLLMENT_PREFIX}/${enrollmentId}/refund`,
  CANCEL: (enrollmentId: number) => `${ENROLLMENT_PREFIX}/${enrollmentId}/cancel`,
  STATUS: (enrollmentId: number) => `${ENROLLMENT_PREFIX}/${enrollmentId}/status`
}