import { BASE } from "../common/base.path";

const REVIEW_FILE_PREFIX = `${BASE}/reviews`;

export const REVIEW_FILE_PATH = {
POST: (reviewId: number) => `${REVIEW_FILE_PREFIX}/${reviewId}/files`,
LIST: (reviewId: number) =>  `${REVIEW_FILE_PREFIX}/${reviewId}/files`,
UPDATE: (fileId: number) =>  `${REVIEW_FILE_PREFIX}/files/${fileId}`,
DELETE: (fileId: number) =>  `${REVIEW_FILE_PREFIX}/files/${fileId}`,

FILES_BY_REVIEW: (reviewId: number) => `${REVIEW_FILE_PREFIX}/${reviewId}/files`
}