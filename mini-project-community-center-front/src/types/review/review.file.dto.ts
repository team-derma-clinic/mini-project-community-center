export interface ReviewFileListDto {
  fileId: number;
  originalName: string;
  storedName: string;
  contentType: string;
  fileSize: number;
  downloadUrl: string;
}

export type BoardListResponse = ReviewFileListDto[];

export interface ReviewFileUpdateReq {
  keepFileIds?: number[];
  files?: File[];
}