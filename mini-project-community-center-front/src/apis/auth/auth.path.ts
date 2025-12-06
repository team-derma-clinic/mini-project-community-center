import { BASE } from "../common/base.path";

const AUTH_PREFIX = `${BASE}/auth`;

// endpoint만 정의
export const AUTH_PATH = {
  STUDENT_SIGNUP: `${AUTH_PREFIX}/student/signup`,
  TEACHER_SIGNUP: `${AUTH_PREFIX}/teacher/signup`,
  STAFF_SIGNUP: `${AUTH_PREFIX}/staff/signup`,
  LOGIN: `${AUTH_PREFIX}/login`,
  LOGOUT: `${AUTH_PREFIX}/logout`,
  REFRESH: `${AUTH_PREFIX}/refresh`,

  PASSWORD_RESET: `${AUTH_PREFIX}/password/reset`,
  PASSWORD_VERIFY: `${AUTH_PREFIX}/password/verify`,
}