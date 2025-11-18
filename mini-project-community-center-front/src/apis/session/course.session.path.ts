import { BASE } from "../common/base.path";

const COURSE_SESSION_PREFIX = `${BASE}/courses`;

export const COURSE_SESSION_PATH = {
  ROOT: `${COURSE_SESSION_PREFIX}/sessions`,

  LIST: (courseId: number) => `${COURSE_SESSION_PREFIX}/${courseId}/sessions`,
  CREATE: (courseId: number) => `${COURSE_SESSION_PREFIX}/${courseId}/sessions`
}