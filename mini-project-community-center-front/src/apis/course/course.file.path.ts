import { BASE } from "../common/base.path";

const COURSE_FILE_PREFIX = `${BASE}/courses`;

export const COURSE_FILE_PATH = {
POST: (courseId: number) => `${COURSE_FILE_PREFIX}/${courseId}/files`,
LIST: (courseId: number) =>  `${COURSE_FILE_PREFIX}/${courseId}/files`,
UPDATE: (courseId: number) =>  `${COURSE_FILE_PREFIX}/${courseId}/files`,
DELETE: (fileId: number) =>  `${COURSE_FILE_PREFIX}/files/${fileId}`,

FILES_BY_COURSE: (courseId: number) => `${COURSE_FILE_PREFIX}/${courseId}/files`,
DOWNLOAD: (fileId: number) => `${COURSE_FILE_PREFIX}/files/${fileId}/download`,

}