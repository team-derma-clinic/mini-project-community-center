import type { EnrollmentMethod, EnrollmentStatus } from "./enrollment.enum.type";

export interface EnrollmentCreateRequest {
  couresId: number;
  method: EnrollmentMethod;
}

export interface EnrollmentListItemResponse {
  id: number;
  courseId: number;
  courseName: string;
  status: EnrollmentStatus;
  enrolledAt: string;
}

export type EnrollmentListResponse = EnrollmentListItemResponse[];

export interface EnrollmentDetailResponse {
  id: number;
  userId:number;
  courseId: number;
  courseName: string;
  status: EnrollmentStatus;
  cancelReason: string;
  enrolledAt: string;
}