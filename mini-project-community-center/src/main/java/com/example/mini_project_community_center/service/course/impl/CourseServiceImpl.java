package com.example.mini_project_community_center.service.course.impl;

import com.example.mini_project_community_center.common.enums.course.CourseCategory;
import com.example.mini_project_community_center.common.enums.course.CourseLevel;
import com.example.mini_project_community_center.common.enums.course.CourseStatus;
import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.course.request.CourseCreateRequest;
import com.example.mini_project_community_center.dto.course.request.CourseStatusUpdateRequest;
import com.example.mini_project_community_center.dto.course.request.CourseUpdateRequest;
import com.example.mini_project_community_center.dto.course.response.CourseDetailResponse;
import com.example.mini_project_community_center.dto.course.response.CourseListItemResponse;
import com.example.mini_project_community_center.entity.center.Center;
import com.example.mini_project_community_center.entity.course.Course;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.repository.center.CenterRepository;
import com.example.mini_project_community_center.repository.course.CourseRepository;
import com.example.mini_project_community_center.repository.user.UserRepository;
import com.example.mini_project_community_center.security.UserPrincipal;
import com.example.mini_project_community_center.service.course.CourseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {
    /** Authorization Checker 추가 */
    private final CourseRepository courseRepository;
    private final CenterRepository centerRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    @PreAuthorize("")
    public ResponseDto<CourseDetailResponse> createCourse(UserPrincipal userPrincipal, CourseCreateRequest req) {
        Center center = centerRepository.findById(req.centerId())
                .orElseThrow(() -> new EntityNotFoundException("해당 센터가 존재하지 않습니다. centerId: " + req.centerId()));

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

        if(req.instructorIds() != null && !req.instructorIds().isEmpty()) {
            List<User> instructors = userRepository.findAllById(req.instructorIds());

            if(instructors.size() != req.instructorIds().size()) {
                throw new EntityNotFoundException("요청한 Instructor Id 중 일부가 존재하지 않는 id 입니다.");
            }
            instructors.forEach(course::addInstructor);
        }
        Course saved = courseRepository.save(course);

        CourseDetailResponse data = CourseDetailResponse.fromEntity(saved);

        return ResponseDto.success(data);
    }

    @Override
    public ResponseDto<Page<CourseListItemResponse>> getCourses(Long centerId, CourseCategory category, CourseLevel level, CourseStatus status, String from, String to, Integer weekday, String timeRange, String q, int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        LocalDate fromDate = (from != null && !from.isBlank()) ? LocalDate.parse(from) : null;
        LocalDate toDate = (to != null && !to.isBlank()) ? LocalDate.parse(to) : null;

        LocalTime startTime = null;
        LocalTime endTime = null;

        if(timeRange != null && timeRange.contains("~")) {
            String[] parts = timeRange.split("~");
            startTime = LocalTime.parse(parts[0]);
            endTime = LocalTime.parse(parts[1]);
        }

        Page<Course> pageResult = courseRepository.searchCourses(
                centerId,
                category,
                level,
                status,
                DateUtils.toKstDateString(fromDate),
                DateUtils.toKstDateString(toDate),
                weekday,
                startTime,
                endTime,
                q,
                pageable
        );

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
        return null;
    }

    @Transactional
    @Override
    @PreAuthorize("")
    public ResponseDto<CourseDetailResponse> updateCourse(UserPrincipal userPrincipal, Long courseId, CourseUpdateRequest req) {
        return null;
    }

    @Transactional
    @Override
    @PreAuthorize("")
    public ResponseDto<CourseDetailResponse> updateCourseStatus(UserPrincipal userPrincipal, Long courseId, CourseStatusUpdateRequest req) {
        return null;
    }

    @Transactional
    @Override
    @PreAuthorize("")
    public ResponseDto<Void> deleteCourse(UserPrincipal userPrincipal, Long courseId) {
        return null;
    }
}
