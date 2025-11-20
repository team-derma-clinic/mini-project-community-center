import type { RoleType } from "../role/role.enum.type";


export interface UserCreateRequest {
  name: string;
  loginId: string;
  password: string;
  email: string;
  phone?: string;
}

export interface UserUpdateRequest {
  name?: string;
  email?: string;
  phone?: string;
}

export interface UserListRequest {
  role?: RoleType;
}

export interface UserDetailResponse {
  id: number;
  name: string;
  loginId: string;
  email: string;
  phone?: string;
  role: RoleType;
  createdAt: string;
  updatedAt: string;
}

export interface UserListItemResponse {
  id: number;
  name: string;
  loginId: string;
  email: string;
  phone?: string;
  role: RoleType;
}

export type UserListResponse = UserListItemResponse[];