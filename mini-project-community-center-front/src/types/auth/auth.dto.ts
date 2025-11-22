import type { RoleType } from "../role/role.enum.type";

export interface SignupRequest {
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

export interface LogoutRequest {
  refreshToken: string;
}

export interface PasswordResetRequest {
  token: string;
  newPassword: string;
  confirmPassword: string;
}

export interface RefreshRequest {
  refreshToken: string;
}

export interface SignupResponse{
  userId: string;
  loginId: string;
  name: string;
  email: string;
}

export interface LoginResponse {
  accessToken: string;
  refreshToken: string;
  accessTokenExpiresInMillis: number;
  role: RoleType;
}

export interface PasswordVerifyResponse {
  valid: boolean;
  email: string;
}