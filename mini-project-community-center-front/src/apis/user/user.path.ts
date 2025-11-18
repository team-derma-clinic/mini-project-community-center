import { BASE } from "../common/base.path";

const USER_PREFIX = `${BASE}/users`;

export const USER_PATH = {
  ROOT: USER_PREFIX,

  LIST: USER_PREFIX,
  CREATE: USER_PREFIX,

  ME: `${USER_PREFIX}/me`,
  UPDATE: `${USER_PREFIX}/me`,

  BY_ID: (userId: number) => `${USER_PREFIX}/${userId}`,

  ROLES: (userId: number) => `${USER_PREFIX}/${userId}/roles`,
  BY_NAME: (userId: number, roleName: string) => `${USER_PREFIX}/${userId}/roles/${roleName}`
}