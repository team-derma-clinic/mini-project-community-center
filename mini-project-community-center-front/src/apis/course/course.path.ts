import { BASE } from "../common/base.path";

const COURSE_PREFIX = `${BASE}/courses`;

export const COURSE_PATH = {
  ROOT: COURSE_PREFIX,

  LIST: COURSE_PREFIX,
  CREATE: COURSE_PREFIX,

  DETAIL: (courseId: number) => `${COURSE_PREFIX}/${courseId}`,
  UPDATE: (courseId: number) => `${COURSE_PREFIX}/${courseId}`,
  BY_COURSE_ID: (courseId: number) => `${COURSE_PREFIX}/${courseId}`,
  STATUS: (courseId: number) => `${COURSE_PREFIX}/${courseId}/status`
}