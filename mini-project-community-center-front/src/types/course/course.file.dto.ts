export interface CourseFileListDto {
  fileId: number;
  originalName: string;
  storedName: string;
  contentType: string;
  fileSize: number;
  downloadUrl: string;
}

export type BoardListResponse = CourseFileListDto[];

export interface CourseFileUpdateReq {
  keepFileIds?: number[];
  files?: File[];
}