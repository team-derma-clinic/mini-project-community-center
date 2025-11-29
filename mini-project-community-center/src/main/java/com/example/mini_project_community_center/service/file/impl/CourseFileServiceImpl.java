package com.example.mini_project_community_center.service.file.impl;

import com.example.mini_project_community_center.common.enums.ErrorCode;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.file.response.CourseFileListResponseDto;
import com.example.mini_project_community_center.entity.course.Course;
import com.example.mini_project_community_center.entity.course.CourseFile;
import com.example.mini_project_community_center.entity.file.FileInfo;
import com.example.mini_project_community_center.exception.FileStorageException;
import com.example.mini_project_community_center.repository.course.CourseRepository;
import com.example.mini_project_community_center.repository.file.CourseFileRepository;
import com.example.mini_project_community_center.service.file.CourseFileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseFileServiceImpl implements CourseFileService {

    private final FileServiceImpl fileService;
    private final CourseFileRepository courseFileRepository;
    private final CourseRepository courseRepository;

    @Transactional
    @Override
    public ResponseDto<Void> uploadThumbnail(Long courseId, MultipartFile file) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("해당 강좌를 찾지 못하였습니다: " + courseId));

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }

        FileInfo info = fileService.saveCourseFile(courseId, file);

        CourseFile courseFile = CourseFile.builder()
                .course(course)
                .fileInfo(info)
                .build();

        courseFileRepository.save(courseFile);

        course.updateThumbnail(info.getId());

        return ResponseDto.success("성공적으로 업로드 되었습니다.");
    }
    @Transactional
    @Override
    public ResponseDto<Void> selectThumbnail(Long courseId, Long fileId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new EntityNotFoundException("해당 강좌를 찾지 못하였습니다: " + courseId));

        CourseFile courseFile = courseFileRepository
                .findByCourseIdAndFileInfoId(courseId, fileId)
                .orElseThrow(() -> new EntityNotFoundException("해당 강좌에 존재하는 파일이 아닙니다: " + fileId));

         course.updateThumbnail(fileId);

        return ResponseDto.success("대표 썸네일이 변경되었습니다.");
    }

    @Override
    public ResponseDto<List<CourseFileListResponseDto>> getFilesByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("해당 강좌를 찾지 못하였습니다: " + courseId));

        List<CourseFile> files = courseFileRepository.findByCourseIdOrderByDisplayOrderAsc(courseId);

        List<CourseFileListResponseDto> data = files.stream()
                .map(CourseFile::getFileInfo)
                .filter(Objects::nonNull)
                .map(fileInfo -> CourseFileListResponseDto.fromEntity(fileInfo))
                .toList();

        return ResponseDto.success(data);
    }

    @Transactional
    @Override
    public ResponseDto<Void> deleteCourseFile(Long fileId) {
        CourseFile courseFile = courseFileRepository.findByFileInfoId(fileId)
                .orElseThrow(() -> new FileStorageException(ErrorCode.INTERNAL_ERROR));

        FileInfo fileInfo = courseFile.getFileInfo();

        courseFileRepository.delete(courseFile);
        fileService.deleteFile(fileInfo);

        return ResponseDto.success("삭제가 완료되었습니다.");
    }

}
