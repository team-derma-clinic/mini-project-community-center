import type { CourseCategory } from "../course/course.enum.type";

// 1. 강좌별 통계(등록/정원/환불)
export interface SearchCourseReport {
  centerId?: number;
  from?: string;
  to?: string;
  sort?: string;
  limit?: number;
}

export interface CourseReportResponse {
  courses: CourseReportItem[];
}

export interface CourseReportItem {
  courseId: number;
  courseName: string;
  centerId: number;
  centerName: string;
  category: CourseCategory;
  enrolledCount: number;
  capacity: number;
  enrollmentRate: number;
  refundedCount: number;
  refundAmount: number;
}

// 2. 출석 통계(세션/강좌/사용자)

// 3. 카테고리별 통계(등록수/평균별점/인기순위)

// 4. 강사별 통계(담당강좌수/평균별점/출석률)
