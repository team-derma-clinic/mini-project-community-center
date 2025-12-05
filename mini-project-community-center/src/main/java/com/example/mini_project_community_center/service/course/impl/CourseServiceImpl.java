package com.example.mini_project_community_center.service.course.impl;

import com.example.mini_project_community_center.common.enums.course.CourseStatus;
import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.dto.PageRequestDto;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.course.request.CourseCreateRequest;
import com.example.mini_project_community_center.dto.course.request.CourseSearchRequest;
import com.example.mini_project_community_center.dto.course.request.CourseStatusUpdateRequest;
import com.example.mini_project_community_center.dto.course.request.CourseUpdateRequest;
import com.example.mini_project_community_center.dto.course.response.CourseDetailResponse;
import com.example.mini_project_community_center.dto.course.response.CourseListItemResponse;
import com.example.mini_project_community_center.dto.user.response.UserListItemResponse;
import com.example.mini_project_community_center.entity.center.Center;
import com.example.mini_project_community_center.entity.course.Course;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.repository.center.CenterRepository;
import com.example.mini_project_community_center.repository.course.course.CourseRepository;
import com.example.mini_project_community_center.repository.course.session.CourseSessionRepository;
import com.example.mini_project_community_center.repository.enrollment.EnrollmentRepository;
import com.example.mini_project_community_center.repository.user.UserRepository;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CenterRepository centerRepository;
    private final UserRepository userRepository;
    private final CourseSessionRepository sessionRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public ResponseDto<CourseDetailResponse> createCourse(UserPrincipal userPrincipal, CourseCreateRequest req) {
        Center center = centerRepository.findById(req.centerId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 센터가 존재하지않습니다. centerId: " + req.centerId()));

        validateDateRange(req.startDate(), req.endDate());

        Course course = Course.create(
                center,
                req.title(),
                req.category(),
                req.level(),
                req.capacity(),
                req.fee(),
                req.status(),
                req.description(),
                LocalDate.parse(req.startDate()),
                LocalDate.parse(req.endDate())
        );

        List<User> instructors = new ArrayList<>();

        if (req.instructorIds() != null && !req.instructorIds().isEmpty()) {
            instructors = userRepository.findAllById(req.instructorIds());

            if (instructors.size() != req.instructorIds().size()) {
                throw new IllegalArgumentException("강사는 최소 1명이상 등록해야합니다.");
            }
            instructors.forEach(course::addInstructor);
        }

        Course saved = courseRepository.save(course);

        List<UserListItemResponse> instructorsList = instructors.stream()
                .map(UserListItemResponse::from)
                .toList();

        CourseDetailResponse data = CourseDetailResponse.fromEntity(saved, instructorsList);

        return ResponseDto.success(data);
    }

    @Override
    public ResponseDto<Page<CourseListItemResponse>> getCourses(CourseSearchRequest searchReq, PageRequestDto pageReq) {
        Pageable pageable = pageReq.toPageable();

        Page<Course> pageResult = courseRepository.searchCourses(searchReq, pageable);

        Page<CourseListItemResponse> data = pageResult.map(course -> new CourseListItemResponse(
                course.getId(),
                course.getCenter().getId(),
                course.getTitle(),
                course.getCategory(),
                course.getLevel(),
                DateUtils.toKstDateString(course.getStartDate()),
                DateUtils.toKstDateString(course.getEndDate())
        ));

        return ResponseDto.success(data);
    }

    @Override
    public ResponseDto<CourseDetailResponse> getCourseDetail(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 강좌가 존재하지 않습니다. courseId: " + courseId));

        List<UserListItemResponse> instructors = course.getInstructors().stream()
                .map(UserListItemResponse::fromCourseInstructor)
                .toList();

        CourseDetailResponse data = CourseDetailResponse.fromEntity(course, instructors);

        return ResponseDto.success(data);
    }

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public ResponseDto<CourseDetailResponse> updateCourse(UserPrincipal userPrincipal, Long courseId, CourseUpdateRequest req) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 강좌가 존재하지 않습니다. courseId: " + courseId));

        validateDateRange(req.startDate(), req.endDate());

        course.updateCenter(
                req.title(),
                req.category(),
                req.level(),
                req.capacity(),
                req.fee(),
                req.status(),
                req.description(),
                LocalDate.parse(req.startDate()),
                LocalDate.parse(req.endDate())
        );

        List<User> instructors = new ArrayList<>();

        if (req.instructorIds() != null) {
            if (!req.instructorIds().isEmpty()) {
                instructors = userRepository.findAllById(req.instructorIds());

                if (instructors.size() != req.instructorIds().size()) {
                    throw new IllegalArgumentException("강사는 최소 1명이상 등록해야합니다.");
                }
            }
            course.updateInstructors(instructors);
        }

        List<UserListItemResponse> instructorList = instructors.stream()
                .map(UserListItemResponse::from)
                .toList();

        CourseDetailResponse data = CourseDetailResponse.fromEntity(course, instructorList);

        return ResponseDto.success(data);
    }

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public ResponseDto<CourseDetailResponse> updateCourseStatus(UserPrincipal userPrincipal, Long courseId, CourseStatusUpdateRequest req) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 강좌가 존재하지 않습니다. courseId: " + courseId));

        CourseStatus newStatus = req.status();
        course.updateStatus(newStatus);

        List<UserListItemResponse> instructors = course.getInstructors().stream()
                .map(i -> UserListItemResponse.from(i.getInstructor()))
                .toList();

        CourseDetailResponse data = CourseDetailResponse.fromEntity(course, instructors);

        return ResponseDto.success(data);
    }

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public ResponseDto<Void> deleteCourse(UserPrincipal userPrincipal, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 강좌가 존재하지 않습니다. courseId: " + courseId));

        if (sessionRepository.existsByCourseId(courseId)) {
            throw new IllegalArgumentException("해당 강좌에는 등록된 세션이 있어 삭제할 수 없습니다.");
        }

        if (enrollmentRepository.existsByCourseId(courseId)) {
            throw new IllegalArgumentException("해당 강좌에 수강 등록한 사용자가 있어 삭제할 수 없습니다.");
        }
        courseRepository.delete(course);

        return ResponseDto.success(null);
    }

    private void validateDateRange(String start, String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작 날짜는 종료 날짜보다 빠를 수 없습니다.");
        }
    }
}
