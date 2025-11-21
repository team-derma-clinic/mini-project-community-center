import type { RoleType, Status } from "./role.enum.type";

export interface RoleRequest {
  roleName: RoleType;
}

export interface AddUserRoleRequest {
  roleName: RoleType;
}

export interface RoleResponse {
  roleName: RoleType;
  status: Status;
}

export type RoleListResponse = RoleResponse[];