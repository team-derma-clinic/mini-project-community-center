import type { AttendanceStatus } from "./attendance.enum.type";

export interface AttendanceCreateRequest {
  sessionId: number;
  userId: number;
  status: AttendanceStatus;
}

export interface AttendanceUpdateRequest {
  status?: AttendanceStatus;
  note?: string;
}

export interface AttendanceSearchParams {
  sessionId?: number;
  userId?: number;
  page?: number;
  size?: number;
  sort?: string;
}

export interface AttendanceDetailResponse {
  id: number;
  sessionId: number;
  userId: number;
  userName: string;
  status: AttendanceStatus;
  note?: string;
  markedAt: string;
}

export interface AttendanceListItemResponse {
  id: number;
  sessionId: number;
  userId: number;
  userName: string;
  status: AttendanceStatus;
  note?: string;
  markedAt: string;
}

export type AttendanceListResponse = AttendanceListItemResponse[];
