import type { CourseCreateRequest, CourseDetailResponse, CourseListResponse } from "@/types/course/course.dto";
import { privateApi, publicApi } from "../common/axiosInstance";
import { COURSE_PATH } from "./course.path";

export const courseApi = {
  createCourse: async (data: CourseCreateRequest): Promise<CourseDetailResponse> => {
    const res = await privateApi.post(COURSE_PATH.CREATE, data);
    return res.data.data;
  },

  getCourses: async (): Promise<CourseListResponse> => {
    const res = await publicApi.get(COURSE_PATH.LIST);
    return res.data.data;
  },

  getCourseDetail: async(courseId: number): Promise<CourseDetailResponse> => {
    const res = await publicApi.get(COURSE_PATH.DETAIL(courseId));
    return res.data.data;
  },

  updateCourse: async(courseId: number): Promise<CourseDetailResponse> => {
    const res = await privateApi.get(COURSE_PATH.UPDATE(courseId));
    return res.data.data;
  }, 

  updateCourseStatus: async(courseId: number): Promise<CourseDetailResponse> => {
    const res = await privateApi.get(COURSE_PATH.STATUS(courseId));
    return res.data.data;
  }
}