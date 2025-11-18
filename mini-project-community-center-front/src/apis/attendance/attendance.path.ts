import { BASE } from "../common/base.path";

const ATTENDANCE_PREFIX = `${BASE}/attendance`;
const COURSE_PREFIX = `${BASE}/courses`;
const SESSION_PREFIX = `${BASE}/sessions`;

export const ATTENDANCE_PATH = {
  ROOT: ATTENDANCE_PREFIX,
  UPSERT: ATTENDANCE_PREFIX,

  DETAIL: (attendanceId: number) => `${ATTENDANCE_PREFIX}/${attendanceId}`,
  UPDATE: (attendanceId: number) => `${ATTENDANCE_PREFIX}/${attendanceId}`,

  COURSE_LIST: (courseId: number) => `${COURSE_PREFIX}/${courseId}/attendance`,
  SESSION_LIST: (sessionId: number) =>
    `${SESSION_PREFIX}/${sessionId}/attendance`,
};
