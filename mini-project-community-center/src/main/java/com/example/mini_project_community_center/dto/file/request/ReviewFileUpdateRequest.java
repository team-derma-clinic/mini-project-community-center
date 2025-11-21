package com.example.mini_project_community_center.dto.file.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ReviewFileUpdateRequest(
        List<Long> keepFileIds,
        List<MultipartFile> files
) {}
