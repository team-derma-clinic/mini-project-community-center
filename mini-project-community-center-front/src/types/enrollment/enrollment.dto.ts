export interface EnrollmentCreateRequest {
  couresId: number;
  method: 'CARD' | 'TRANSFER';
}

export interface EnrollmentListItemResponse {
  id: number;
  courseId: number;
  courseName: string;
  status: 'PENDING' | 'CONFIRMED' | 'CANCELED' | 'REFUNDED';
  enrolledAt: string;
}

export type EnrollmentListResponse = EnrollmentListItemResponse[];

export interface EnrollmentDetailResponse {
  id: number;
  userId:number;
  courseId: number;
  courseName: string;
  status: 'PENDING' | 'CONFIRMED' | 'CANCELED' | 'REFUNDED';
  cancelReason: string;
  enrolledAt: string;
}