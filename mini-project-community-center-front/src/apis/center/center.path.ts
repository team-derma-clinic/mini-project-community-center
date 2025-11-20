import { BASE } from "../common/base.path";

const CENTER_PREFIX = `${BASE}/centers`;

export const CENTER_PATH = {
  ROOT: CENTER_PREFIX,

  LIST: CENTER_PREFIX,
  CREATE: CENTER_PREFIX,
  
  DETAIL: (centerId: number) => `${CENTER_PREFIX}/${centerId}`,
  UPDATE: (centerId: number) => `${CENTER_PREFIX}/${centerId}`,
  BY_CENTER_ID: (centerId: number) => `${CENTER_PREFIX}/${centerId}`
}