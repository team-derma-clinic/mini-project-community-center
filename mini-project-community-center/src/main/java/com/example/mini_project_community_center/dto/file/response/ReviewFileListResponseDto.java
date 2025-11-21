package com.example.mini_project_community_center.dto.file.response;

public record ReviewFileListResponseDto (
        Long fileId,
        String originalName,
        String storedName,
        String contentType,
        Long fileSize,
        String downloadUrl
) {}
