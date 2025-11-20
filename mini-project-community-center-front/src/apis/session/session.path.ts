import { BASE } from "../common/base.path";

const SESSION_PREFIX = `${BASE}/sessions`;

export const SESSION_PATH = {
  ROOT: `${SESSION_PREFIX}`,

  DETAIL: (sessionId: number) => `${SESSION_PREFIX}/${sessionId}`,
  UPDATE: (sessionId: number) => `${SESSION_PREFIX}/${sessionId}`,
  BY_SESSION_ID: (sessionId: number) => `${SESSION_PREFIX}/${sessionId}`,
  STATUS: (sessionId: number) => `${SESSION_PREFIX}/${sessionId}/status`
}