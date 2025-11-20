import type { RoleType } from "../role/role.dto";

export interface UserCreateRequest {
  name: string;
  loginId: string;
  password: string;
  email: string;
  phone?: string;
}

export interface UserUpdateRequest {
  email?: string;
  phone?: string;
}

export interface UserDetailResponse {
  id: number;
  name: string;
  loginId: string;
  email: string;
  phone?: string;
  roles: string[];
  createdAt: string;
  updatedAt: string;
}

export interface UserListItemResponse {
  id: number;
  name: string;
  loginId: string;
  email: string;
  phone?: string;
  roles: RoleType[];
}

export type UserListResponse = UserListItemResponse[];