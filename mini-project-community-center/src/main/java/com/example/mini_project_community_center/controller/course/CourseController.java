package com.example.mini_project_community_center.controller.course;

import com.example.mini_project_community_center.common.apis.CourseApi;
import com.example.mini_project_community_center.dto.PageRequestDto;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.course.request.CourseCreateRequest;
import com.example.mini_project_community_center.dto.course.request.CourseSearchRequest;
import com.example.mini_project_community_center.dto.course.request.CourseStatusUpdateRequest;
import com.example.mini_project_community_center.dto.course.request.CourseUpdateRequest;
import com.example.mini_project_community_center.dto.course.response.CourseDetailResponse;
import com.example.mini_project_community_center.dto.course.response.CourseListItemResponse;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.course.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CourseApi.ROOT)
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ResponseDto<CourseDetailResponse>> createCourse(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody CourseCreateRequest req
    ) {
        ResponseDto<CourseDetailResponse> data = courseService.createCourse(userPrincipal, req);
        return ResponseEntity.ok(data);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<Page<CourseListItemResponse>>> getCourses(
            @Valid @ModelAttribute CourseSearchRequest searchReq,
            @Valid @ModelAttribute PageRequestDto pageReq
    ) {
        ResponseDto<Page<CourseListItemResponse>> data = courseService.getCourses(searchReq, pageReq);

        return ResponseEntity.ok(data);
    }

    @GetMapping(CourseApi.BY_COURSE_ID_ONLY)
    public ResponseEntity<ResponseDto<CourseDetailResponse>> getCourseDetail(
            @PathVariable Long courseId
    ) {
        ResponseDto<CourseDetailResponse> data = courseService.getCourseDetail(courseId);
        return ResponseEntity.ok(data);
    }

    @PutMapping(CourseApi.BY_COURSE_ID_ONLY)
    public ResponseEntity<ResponseDto<CourseDetailResponse>> updateCourse(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long courseId,
            @Valid @RequestBody CourseUpdateRequest req
    ) {
        ResponseDto<CourseDetailResponse> data = courseService.updateCourse(userPrincipal, courseId, req);
        return ResponseEntity.ok(data);
    }

    @PutMapping(CourseApi.STATUS)
    public ResponseEntity<ResponseDto<CourseDetailResponse>> updateCourseStatus(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long courseId,
            @Valid @RequestBody CourseStatusUpdateRequest req
    ) {
        ResponseDto<CourseDetailResponse> data = courseService.updateCourseStatus(userPrincipal, courseId, req);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping(CourseApi.BY_COURSE_ID_ONLY)
    public ResponseEntity<ResponseDto<Void>> deleteCourse(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long courseId) {
        ResponseDto<Void> data = courseService.deleteCourse(userPrincipal, courseId);
        return ResponseEntity.ok(data);
    }

}
