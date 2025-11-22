export interface CourseFileCreateReqest {
  courseId: number;
  files: File[];
}

export interface CourseFileUpdateRequest {
  keepFileIds?: number[];
  files?: File[];
}

export interface CourseFileResponse {
  fileId: number;
  originalName: string;
  storedName: string;
  contentType: string;
  fileSize: number;
  downloadUrl: string;
}

export type CourseFileListResponse = CourseFileResponse[];
