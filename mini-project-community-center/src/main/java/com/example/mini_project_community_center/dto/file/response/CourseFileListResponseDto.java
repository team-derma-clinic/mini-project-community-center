package com.example.mini_project_community_center.dto.file.response;

import com.example.mini_project_community_center.entity.file.FileInfo;
import lombok.Builder;

@Builder
public record CourseFileListResponseDto(
        Long fileId,
        String originalName,
        String storedName,
        String contentType,
        Long fileSize,
        String downloadUrl
) {
    public static CourseFileListResponseDto fromEntity(FileInfo fileInfo, String baseDownloadUrl) {
        if (fileInfo == null) return null;

        return CourseFileListResponseDto.builder()
                .fileId(fileInfo.getId())
                .originalName(fileInfo.getOriginalName())
                .storedName(fileInfo.getStoredName())
                .contentType(fileInfo.getContentType())
                .fileSize(fileInfo.getFileSize())
                .downloadUrl(baseDownloadUrl + fileInfo.getId())
                .build();
    }
}
