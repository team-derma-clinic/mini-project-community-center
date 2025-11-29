package com.example.mini_project_community_center.controller.file;

import com.example.mini_project_community_center.common.apis.CourseFileApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.file.response.CourseFileListResponseDto;
import com.example.mini_project_community_center.service.file.CourseFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(CourseFileApi.ROOT)
@RequiredArgsConstructor
public class CourseFileController {
    private final CourseFileService courseFileService;

    @PostMapping(CourseFileApi.UPLOAD) // 썸네일 업로드
    public ResponseEntity<ResponseDto<Void>> uploadThumbnail(
            @PathVariable Long courseId,
            @RequestParam("files")MultipartFile file
    ) {
        ResponseDto<Void> data = courseFileService.uploadThumbnail(courseId, file);
        return ResponseEntity.ok(data);
    }

    @GetMapping(CourseFileApi.LIST) // 강좌의 모든 업로드 파일 조회
    public ResponseEntity<ResponseDto<List<CourseFileListResponseDto>>> getCourseFiles(
            @PathVariable Long courseId
    ) {
        List<CourseFileListResponseDto> files = courseFileService.getCourseFiles(courseId);

        return ResponseEntity.ok(ResponseDto.success(files));
    }

    @DeleteMapping(CourseFileApi.DELETE) // 썸네일 삭제
    public ResponseEntity<ResponseDto<Void>> deleteCourseFile(
            @PathVariable Long fileId
    ) {
        ResponseDto<Void> data = courseFileService.deleteCourseFile(fileId);
        return ResponseEntity.ok(data);
    }
}
