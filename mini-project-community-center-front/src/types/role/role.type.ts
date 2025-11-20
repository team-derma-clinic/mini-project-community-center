import type { RoleType } from "./role.enum.type";

export interface AddUserRoleForm {
  roleName: RoleType;
}

export interface RemoveUserRoleForm {
  roleName: RoleType;
}