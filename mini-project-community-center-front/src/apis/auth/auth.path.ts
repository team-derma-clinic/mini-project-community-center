import { BASE } from "../common/base.path";

const AUTH_PREFIX = `${BASE}/auth`;

// endpoint만 정의
export const AUTH_PATH = {
  SIGNUP: `${AUTH_PREFIX}/signup`,
  STUDENT_LOGIN: `${AUTH_PREFIX}/login`,
  TEACHER_LOGIN: `${AUTH_PREFIX}/teacher/login`,
  STAFF_LOGIN: `${AUTH_PREFIX}/staff/login`,
  LOGOUT: `${AUTH_PREFIX}/logout`,
  REFRESH: `${AUTH_PREFIX}/refresh`,

  PASSWORD_RESET: `${AUTH_PREFIX}/password/reset`,
  PASSWORD_VERIFY: `${AUTH_PREFIX}/password/verify`,
}