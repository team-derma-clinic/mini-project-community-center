package com.example.mini_project_community_center.service.course;

import com.example.mini_project_community_center.dto.PageRequestDto;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.course.request.CourseCreateRequest;
import com.example.mini_project_community_center.dto.course.request.CourseSearchRequest;
import com.example.mini_project_community_center.dto.course.request.CourseStatusUpdateRequest;
import com.example.mini_project_community_center.dto.course.request.CourseUpdateRequest;
import com.example.mini_project_community_center.dto.course.response.CourseDetailResponse;
import com.example.mini_project_community_center.dto.course.response.CourseListItemResponse;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface CourseService {
    ResponseDto<CourseDetailResponse> createCourse(UserPrincipal userPrincipal, @Valid CourseCreateRequest req);

    ResponseDto<Page<CourseListItemResponse>> getCourses(@Valid CourseSearchRequest searchReq, @Valid PageRequestDto pageReq);

    ResponseDto<CourseDetailResponse> getCourseDetail(Long courseId);

    ResponseDto<CourseDetailResponse> updateCourse(UserPrincipal userPrincipal, Long courseId, @Valid CourseUpdateRequest req);

    ResponseDto<CourseDetailResponse> updateCourseStatus(UserPrincipal userPrincipal, Long courseId, @Valid CourseStatusUpdateRequest req);

    ResponseDto<Void> deleteCourse(UserPrincipal userPrincipal, Long courseId);
}
