import type { CourseCategory, CourseLevel, CourseStatus } from "./course.enum.type";

export interface CourseCreateRequest {
  centerId: number;
  title: string; 
  category: CourseCategory;
  level: CourseLevel;
  capacity: number;
  fee: number;
  status: CourseStatus;
  description: string;
  instructorIds: number[];
  startDate: string;
  endDate: string;
}

export interface CourseUpdateRequest {
  title: string; 
  category: CourseCategory;
  level: CourseLevel; 
  capacity: number;
  fee: number;
  status: CourseStatus;
  description: string;
  instructorIds: number[];
  startDate: string;
  endDate: string;
}

export interface CourseStatusUpdateRequest {
  status: CourseStatus;
}
export interface CourseListItemResponse {
  id: number;
  centerId: number;
  title: string;
  category: CourseCategory;
  level: CourseLevel;
  startDate: string;
  endDate: string;
  capacity: number;
  currentEnrollment: number;
  status: CourseStatus;
}

export type CourseListResponse = CourseListItemResponse[];

export interface CourseSearchParams {
  centerId?: number;
  category?: CourseCategory;
  level?: CourseLevel;
  status?: CourseStatus;
  from?: string;
  to?: string;
  weekday?: number;
  timeRange?: string;
  q?: string;
  page: number;
  size: number;
  sort: string;
}

export interface CourseDetailResponse {
  id: number;
  centerId: number;
  title: string; 
  category: CourseCategory;
  level: CourseLevel; 
  capacity: number;
  currentEnrollment: number;
  fee: number;
  status: CourseStatus;
  description: string;
  instructorIds: number[];
  startDate: string;
  endDate: string;
  createdAt: string;
  updatedAt: string;
}