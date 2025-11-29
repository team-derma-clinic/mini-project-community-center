package com.example.mini_project_community_center.service.file.impl;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.entity.course.Course;
import com.example.mini_project_community_center.entity.course.CourseFile;
import com.example.mini_project_community_center.entity.file.FileInfo;
import com.example.mini_project_community_center.repository.course.CourseRepository;
import com.example.mini_project_community_center.repository.file.CourseFileRepository;
import com.example.mini_project_community_center.repository.file.FileInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl {

    private final FileServiceImpl fileService;
    private final FileInfoRepository fileInfoRepository;
    private final CourseFileRepository courseFileRepository;
    private final CourseRepository courseRepository;

    private final int MAX_ATTACH = 1;

    @Transactional
    public ResponseDto<Void> uploadCourseFile(Long courseId, MultipartFile file) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("해당 강좌를 찾지 못하였습니다: " + courseId));

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }

        if (courseFileRepository.existsByCourseId(courseId)) {
            throw new IllegalStateException("해당 강좌에는 이미 파일이 존재합니다.");
        }

        FileInfo info = fileService.saveCourseFile(courseId, file);
        if (info == null) {
            throw new IllegalStateException("파일 저장 중 오류가 발생했습니다.");
        }

        CourseFile courseFile = CourseFile.builder()
                .course(course)
                .fileInfo(info)
                .build();

        courseFileRepository.save(courseFile);

        return ResponseDto.success("성공적으로 업로드 되었습니다.");
    }


}
