import type { RoleType } from "./role.enum.type";

export interface UserRoleAddRequest {
  roleName: RoleType;
}

export interface UserRoleRemoveRequest {
  roleName: RoleType;
}

export interface RoleResponse {
  roleName: RoleType;
}

export type RoleListResponse = RoleResponse[];