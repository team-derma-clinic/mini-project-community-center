import type { CourseCategory, CourseLevel, CourseStatus } from "./course.enum.type";

export interface CreateCourseForm {
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

export interface UpdateCourseForm {
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
