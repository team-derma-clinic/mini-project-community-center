import { BASE } from "../common/base.path";

const AUTH_PREFIX = `${BASE}/auth`;

// endpoint만 정의
export const AUTH_PATH = {
  REGISTER: `${AUTH_PREFIX}/register`,
  LOGIN: `${AUTH_PREFIX}/login`,
  LOGOUT: `${AUTH_PREFIX}/logout`,
  REFRESH: `${AUTH_PREFIX}/refresh`,

  PASSWORD_RESET: `${AUTH_PREFIX}/password/reset`,
  PASSWORD_VERIFY: `${AUTH_PREFIX}/password/verify`,
}