import { BASE } from "../common/base.path";

const REVIEW_PREFIX = `${BASE}/reviews`;
const COURSE_PREFIX = `${BASE}/courses`;

export const REVIEW_PATH = {
  ROOT: REVIEW_PREFIX,
  CREATE: REVIEW_PREFIX,

  MY_REVIEWS: `${REVIEW_PREFIX}/me`,

  COURSE_REVIEWS: (courseId: number) => `${COURSE_PREFIX}/${courseId}/reviews`,

  UPDATE: (reviewId: number) => `${REVIEW_PREFIX}/${reviewId}`,
  DELETE: (reviewId: number) => `${REVIEW_PREFIX}/${reviewId}`,
};
