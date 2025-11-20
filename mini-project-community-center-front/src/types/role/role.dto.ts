export type RoleType = "STUDENT" | "INSTRUCTOR" | "STAFF" | "ADMIN";

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