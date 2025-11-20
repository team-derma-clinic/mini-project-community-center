import type { AttendanceStatus } from "./attendance.enum.type";

export interface CreateAttendanceRequest {
    sessionId: number;
    userId: number;
    status: AttendanceStatus;
}

export interface UpdateAttendanceRequest {
    status?: AttendanceStatus;
    note?: string;
}

export interface SearchCourseAttendance {
    sessionId?: number;
    userId?: number;
    page?: number;
    size?: number;
    sort?: string;
}

export interface SearchSessionAttendance {
    page?: number;
    size?: number;
    sort?: string;
}

export interface AttendanceListItemResponse {
    id: number;
    sessionId: number;
    userId: number;
    userName?: string;
    status: AttendanceStatus;
    note?: string;
    markedAt: string;
}

export type AttendanceListResponse = AttendanceListItemResponse[];

export interface AttendanceDetailResponse {
    id: number;
    sessionId: number;
    userId: number;
    userName?: string;
    status: AttendanceStatus;
    note?: string;
    markedAt: string;
}