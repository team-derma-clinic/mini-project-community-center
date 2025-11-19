import type { CourseCategory, CourseLevel, CourseStatus } from "./course.enum.type";

export interface CourseCreateForm {
  centerId: number;
  title: string; 
  category: CourseCategory;
  level: CourseLevel; 
  capacity: string;
  fee: string;
  status: CourseStatus;
  description: string;
  instructorIds: number[];
  startDate: string;
  endDate: string;
}

export interface CourseUpdateForm {
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

export interface CourseStatusUpdateForm {
  status: CourseStatus;
}