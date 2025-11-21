import { BASE } from "../common/base.path";

const REPORT_PREFIX = `${BASE}/reports`;

export const REPORT_PATH = {
  ROOT: REPORT_PREFIX,

  COURSES: `${REPORT_PREFIX}/courses`,
  ATTENDANCE: `${REPORT_PREFIX}/attendance`,
  CATEGORIES: `${REPORT_PREFIX}/categories`,
  INSTRUCTORS: `${REPORT_PREFIX}/instructors`,
};
