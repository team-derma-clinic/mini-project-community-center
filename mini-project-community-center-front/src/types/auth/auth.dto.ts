import type { RoleType } from "../role/role.dto";

export interface RegisterRequest {
  loginId: string;
  password: string;
  name: string;
  email: string;
  phone?: string;
}

export interface LoginRequest {
  loginId: string;
  password: string;
}

export interface PasswordChangeRequest {
  password: string;
  newPassword: string;
}

export interface LoginResponse {
  tokenType: string;
  accessToken: string;
  loginId: string;
  roles: RoleType[];
}