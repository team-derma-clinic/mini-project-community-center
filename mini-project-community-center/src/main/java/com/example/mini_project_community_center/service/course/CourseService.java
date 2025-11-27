package com.example.mini_project_community_center.service.course;

import com.example.mini_project_community_center.common.enums.course.CourseCategory;
import com.example.mini_project_community_center.common.enums.course.CourseLevel;
import com.example.mini_project_community_center.common.enums.course.CourseStatus;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.course.request.CourseCreateRequest;
import com.example.mini_project_community_center.dto.course.request.CourseStatusUpdateRequest;
import com.example.mini_project_community_center.dto.course.request.CourseUpdateRequest;
import com.example.mini_project_community_center.dto.course.response.CourseDetailResponse;
import com.example.mini_project_community_center.dto.course.response.CourseListItemResponse;
import com.example.mini_project_community_center.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface CourseService {
    ResponseDto<CourseDetailResponse> createCourse(UserPrincipal userPrincipal, @Valid CourseCreateRequest req);

    ResponseDto<Page<CourseListItemResponse>> getCourses(Long centerId, CourseCategory category, CourseLevel level, CourseStatus status, String from, String to, Integer weekday, String timeRange, String q, int page, int size, String sort);

    ResponseDto<CourseDetailResponse> getCourseDetail(Long courseId);

    ResponseDto<CourseDetailResponse> updateCourse(UserPrincipal userPrincipal, Long courseId, @Valid CourseUpdateRequest req);

    ResponseDto<CourseDetailResponse> updateCourseStatus(UserPrincipal userPrincipal, Long courseId, @Valid CourseStatusUpdateRequest req);

    ResponseDto<Void> deleteCourse(UserPrincipal userPrincipal, Long courseId);
}
