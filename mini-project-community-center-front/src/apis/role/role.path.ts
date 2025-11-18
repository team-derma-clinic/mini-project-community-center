import { BASE } from "../common/base.path";

const ROLE_PREFIX = `${BASE}/users`;

export const ROLE_PATH = {
  ROOT: (userId: number) => `${ROLE_PREFIX}/${userId}/roles`,
  BY_NAME: (userId: number, roleName: string) => `${ROLE_PREFIX}/${userId}/${roleName}`
}