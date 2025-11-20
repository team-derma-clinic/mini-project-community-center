import type { CourseListResponse } from "../course/course.dto";

export interface CenterCreateRequest {
  name: string;
  address?: string;
  latitude?: number;
  longitude?: number;
  phone?: string;
}

export interface CenterUpdateRequest {
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

export interface CenterSearchParams {
  q?: string;
  page: number;
  size: number;
  sort: string;
}

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