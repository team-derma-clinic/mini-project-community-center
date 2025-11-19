import type { CourseListResponse } from "../course/course.dto";

export interface CreateCenterRequest {
  name: string;
  address?: string;
  latitude?: number;
  longitude?: number;
  phone?: string;
}

export interface UpdateCenterRequest {
  name: string;
  address?: string;
  latitude?: number;
  longitude?: number;
  phone?: string;
}

export interface CenterListItemResponse {
  id: number;
  name: string;
  address?: string;
}

export type CenterListResponse = CenterListItemResponse[];

export interface CenterDetailResponse {
  id: number;
  name: string;
  address?: string;
  latitude?: number;
  longitude?: number;
  phone?: string;
  createdAt: string;
  courses?: CourseListResponse[];
}