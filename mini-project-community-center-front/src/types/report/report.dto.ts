import type { CourseListItemResponse } from "../course/course.dto";
import type { CourseCategory } from "../course/course.enum.type";

export interface SearchCourseReport {
  centerId?: number;
  from?: string;
  to?: string;
  page?: number;
  size?: number;
  sort?: string;
}

export interface CourseReportResponse {
  courses: CourseListItemResponse[];
  total: number;
  page: number;
  size: number;
  totalPages: number;
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
