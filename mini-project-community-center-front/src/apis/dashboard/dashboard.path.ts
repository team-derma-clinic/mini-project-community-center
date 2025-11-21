import { BASE } from "../common/base.path";

const INSTRUCTOR_DASHBOARD_PREFIX = `${BASE}/instructors/me/dashboard`;
const STAFF_DASHBOARD_PREFIX = `${BASE}/staff/dashboard`;

export const DASHBOARD_PATH = {
  INSTRUCTOR_ME: INSTRUCTOR_DASHBOARD_PREFIX,
  STAFF: STAFF_DASHBOARD_PREFIX,
};
