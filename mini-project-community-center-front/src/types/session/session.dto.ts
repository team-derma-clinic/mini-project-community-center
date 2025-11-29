import type { SessionStatus } from "./session.enum.type";

export interface SessionCreateRequest {
  startTime: string;
  endTime: string;
  room?: string;
  status: SessionStatus;
}

export interface SessionUpdateRequest {
  startTime: string;
  endTime: string;
  room?: string;
  status: SessionStatus;
}

export interface SessionStatusUpdateRequest {
  status: SessionStatus;
}

export interface SessionListItemResponse {
  id: number;
  courseId: number;
  startTime: string;
  endTime: string;
  room?: string;
  status: SessionStatus;
}

export type SessionListResponse = SessionListItemResponse[];

export interface SessionSearchParams {
  from?: string;
  to?: string;
  weekday?: number;
  timeRange?: string;
  page?: number;
  size?: number;
  sort?: string;
}

export interface SessionDetailResponse {
  id: number;
  courseId: number;
  startTime: string;
  endTime: string;
  room?: string;
  status: SessionStatus;
  createdAt: string;
  updatedAt: string;
}



