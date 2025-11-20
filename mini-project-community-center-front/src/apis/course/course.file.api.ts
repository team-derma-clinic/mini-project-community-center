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

}