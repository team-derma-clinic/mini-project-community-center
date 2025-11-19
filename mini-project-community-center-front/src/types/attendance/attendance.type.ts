import type { AttendanceStatus } from "./attendance.enum.type";

export interface CreateAttendanceForm {
  sessionId: number;
  userId: number;
  status: AttendanceStatus;
  note?: string;
}

export interface UpdateAttendanceForm {
  status?: AttendanceStatus;
  note?: string;
}
