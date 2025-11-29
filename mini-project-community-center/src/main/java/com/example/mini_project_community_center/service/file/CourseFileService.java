package com.example.mini_project_community_center.service.file;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.file.response.CourseFileListResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseFileService {
    ResponseDto<Void> uploadThumbnail(Long courseId, MultipartFile file);

    List<CourseFileListResponseDto> getCourseFiles(Long courseId);

    ResponseDto<Void> deleteCourseFile(Long fileId);
}
