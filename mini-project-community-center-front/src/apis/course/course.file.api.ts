import type { CourseFileListDto } from "@/types/course/course.file.dto";
import { publicApi } from "../common/axiosInstance"
import { COURSE_FILE_PATH } from "./course.file.path"

export const courseFileApi = {
  uploadCourseFiles: async(courseId: number, formData: FormData) => {
    const res = publicApi.post<void>(COURSE_FILE_PATH.POST(courseId),
    formData,
    { headers: {"Content-Type": "multipart/form-data"}}
  );
  return (await res).data;
  },

  getFilesByReview: async(courseId: number) => {
      const res = await publicApi.get<CourseFileListDto>(
        COURSE_FILE_PATH.LIST(courseId)
      );
      console.log("data: ", res.data);
      return res.data;
    },
  
    updateBoardFiles: async(fileId: number, formData: FormData) => {
      const res = await publicApi.put<void>(
        COURSE_FILE_PATH.UPDATE(fileId),
        formData,
        { headers: {"Content-Type": "multipart/form-data"}}
      );
      return res.data;
    },
  
    deleteBoardFile: async(fileId: number):Promise<void> => {
      await publicApi.delete(COURSE_FILE_PATH.DELETE(fileId));
    },

}