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
                .orElseThrow(() -> new EntityNotFoundException("해당 강좌를 찾지못하였습니다: " + courseId));

        if (file.isEmpty()) {
            throw new IllegalArgumentException("한 개의 사진만 업로드 할 수 있습니다.");
        }

        FileInfo info = fileService.saveCourseFile(courseId, file);
        if (info == null) continue;

        CourseFile courseFile = CourseFile.


    }


}
