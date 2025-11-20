import type { RoleType } from "./role.enum.type";

export interface RoleResponse {
  roleName: RoleType;
}

export type RoleListResponse = RoleResponse[];