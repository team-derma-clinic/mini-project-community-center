import type { AttendanceStatus } from "./attendance.enum.type";

export interface AttendanceCreateForm {
  sessionId: number;
  userId: number;
  status: AttendanceStatus;
}

export interface AttendanceUpdateForm {
  status?: AttendanceStatus;
  note?: string;
}
