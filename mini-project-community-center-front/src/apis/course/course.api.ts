import type { CourseCreateRequest, CourseDetailResponse, CourseListResponse, CourseStatusUpdateRequest, CourseUpdateRequest } from "@/types/course/course.dto";
import { privateApi, publicApi } from "../common/axiosInstance";
import { COURSE_PATH } from "./course.path";
import type { ApiResponse } from "@/types/common/ApiResponse";

export const courseApi = {
  createCourse: async (data: CourseCreateRequest): Promise<CourseDetailResponse> => {
    const res = await privateApi.post<ApiResponse<CourseDetailResponse>>(COURSE_PATH.CREATE, data);
    return res.data.data;
  },

  getCourses: async (): Promise<CourseListResponse> => {
    const res = await publicApi.get<ApiResponse<CourseListResponse>>(COURSE_PATH.LIST);
    return res.data.data;
  },

  getCourseDetail: async(courseId: number): Promise<CourseDetailResponse> => {
    const res = await publicApi.get<ApiResponse<CourseDetailResponse>>(COURSE_PATH.DETAIL(courseId));
    return res.data.data;
  },

  updateCourse: async(courseId: number, data: CourseUpdateRequest): Promise<CourseDetailResponse> => {
    const res = await privateApi.put<ApiResponse<CourseDetailResponse>>(COURSE_PATH.UPDATE(courseId), data);
    return res.data.data;
  }, 

  updateCourseStatus: async(courseId: number, data: CourseStatusUpdateRequest): Promise<CourseDetailResponse> => {
    const res = await privateApi.put<ApiResponse<CourseDetailResponse>>(COURSE_PATH.STATUS(courseId), data);
    return res.data.data;
  },

  deleteCourse: async(courseId: number): Promise<void> => {
    await privateApi.delete<ApiResponse<void>>(COURSE_PATH.BY_COURSE_ID(courseId));
  }
}