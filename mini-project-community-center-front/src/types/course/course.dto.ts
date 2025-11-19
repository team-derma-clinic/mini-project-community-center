import type { CourseCategory, CourseLevel, CourseStatus } from "./course.enum.type";

export interface CreateCourseRequest {
  centerId: number;
  title: string; 
  category: CourseCategory;
  level: CourseLevel;
  capacity: string;
  fee: number;
  status: CourseStatus;
  description: string;
  instructorIds: number[];
  startDate: string;
  endDate: string;
}

export interface UpdateCourseRequest {
  title?: string; 
  category?: CourseCategory;
  level?: CourseLevel; 
  capacity?: string;
  fee?: number;
  status?: CourseStatus;
  description?: string;
  instructorIds?: number[];
  startDate?: string;
  endDate?: string;
}

export interface CourseListItemResponse {
  id: number;
  title: string;
  category: CourseCategory;
  level: CourseLevel;
  startDate: string;
  endDate: string;
}

export type CourseListResponse = CourseListItemResponse[];

export interface CourseDetailResponse {
  id: number;
  centerId: number;
  title: string; 
  category: string;
  level?: string; 
  capacity: string;
  fee: number;
  status?: string;
  description: string;
  instructorIds: number[];
  startDate: string;
  endDate: string;
  createdAt: string;
  updatedAt: string;
}