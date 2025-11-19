import type { SessionStatus } from "./session.enum.type";

export interface SessionCreateForm {
  startTime: string;
  endTime: string;
  room?: string;
  status: SessionStatus;
}

export interface SessionUpdateForm {
  startTime?: string;
  endTime?: string;
  room?: string;
  status?: SessionStatus;
}

export interface SessionStatusUpdateForm {
  status: SessionStatus;
}