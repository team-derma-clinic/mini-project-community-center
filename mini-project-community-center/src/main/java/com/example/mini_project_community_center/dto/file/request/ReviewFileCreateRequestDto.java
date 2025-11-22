package com.example.mini_project_community_center.dto.file.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ReviewFileCreateRequestDto(
        @NotNull
        Long reviewId,
        List<MultipartFile> files
) {}
