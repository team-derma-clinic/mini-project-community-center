export interface ReviewFileCreateReqest {
  reviewId: number;
  files: File[];
}

export interface ReviewFileUpdateRequest {
  keepFileIds?: number[];
  files?: File[];
}

export interface ReviewFileResponse {
  fileId: number;
  originalName: string;
  storedName: string;
  contentType: string;
  fileSize: number;
  downloadUrl: string;
}

export type ReviewFileListResponse = ReviewFileResponse[];
