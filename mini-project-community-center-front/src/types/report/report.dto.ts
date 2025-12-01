// 1. 강좌별 통계(등록/정원/환불)
export interface CourseReportRequest {
  centerId?: number;
  from?: string;
  to?: string;
  sort?: string;
  limit?: number;
}

export interface CourseReportResponse {
  courseId: number;
  courseName: string;
  capacity: number;
  refundCount: number;
  enrollmentRate: number;
}

// 2. 출석 통계(세션/강좌/사용자)

// 3. 카테고리별 통계(등록수/평균별점/인기순위)

// 4. 강사별 통계(담당강좌수/평균별점/출석률)
