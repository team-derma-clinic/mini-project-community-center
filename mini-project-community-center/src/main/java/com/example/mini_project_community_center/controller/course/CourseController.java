package com.example.mini_project_community_center.controller.course;

import com.example.mini_project_community_center.common.apis.CourseApi;
import com.example.mini_project_community_center.common.enums.course.CourseCategory;
import com.example.mini_project_community_center.common.enums.course.CourseLevel;
import com.example.mini_project_community_center.common.enums.course.CourseStatus;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.course.request.CourseCreateRequest;
import com.example.mini_project_community_center.dto.course.request.CourseSearchRequest;
import com.example.mini_project_community_center.dto.course.request.CourseStatusUpdateRequest;
import com.example.mini_project_community_center.dto.course.request.CourseUpdateRequest;
import com.example.mini_project_community_center.dto.course.response.CourseDetailResponse;
import com.example.mini_project_community_center.dto.course.response.CourseListItemResponse;
import com.example.mini_project_community_center.security.UserPrincipal;
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

    // 강좌 생성(STAFF/ADMIN)
    @PostMapping
    public ResponseEntity<ResponseDto<CourseDetailResponse>> createCourse(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody CourseCreateRequest req
            ) {
        ResponseDto<CourseDetailResponse> data = courseService.createCourse(userPrincipal, req);
        return ResponseEntity.ok(data);
    }

    // 강좌 목록/검색 (Public)
    @GetMapping
    public ResponseEntity<ResponseDto<Page<CourseListItemResponse>>> getCourses(
            @Valid CourseSearchRequest req
            ) {
        ResponseDto<Page<CourseListItemResponse>> data = courseService.getCourses(
                req);

        return ResponseEntity.ok(data);
    }

    // 강좌 상세 조회(Public)
    @GetMapping(CourseApi.BY_COURSE_ID)
    public ResponseEntity<ResponseDto<CourseDetailResponse>> getCourseDetail(
            @PathVariable Long courseId
    ) {
        ResponseDto<CourseDetailResponse> data = courseService.getCourseDetail(courseId);
        return ResponseEntity.ok(data);
    }

    // 강좌 수정(STAFF/ADMIN)
    @PutMapping(CourseApi.BY_COURSE_ID)
    public ResponseEntity<ResponseDto<CourseDetailResponse>> updateCourse(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long courseId,
            @Valid @RequestBody CourseUpdateRequest req
            ) {
        ResponseDto<CourseDetailResponse> data= courseService.updateCourse(userPrincipal, courseId, req);
        return ResponseEntity.ok(data);
    }


    // 강좌 상태 변경(STAFF/ADMIN)
    @PutMapping(CourseApi.STATUS)
    public ResponseEntity<ResponseDto<CourseDetailResponse>> updateCourseStatus(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long courseId,
            @Valid @RequestBody CourseStatusUpdateRequest req
    ) {
        ResponseDto<CourseDetailResponse> data = courseService.updateCourseStatus(userPrincipal, courseId, req);
        return ResponseEntity.ok(data);
    }

    // 강좌 삭제(STAFF/ADMIN)
    @DeleteMapping(CourseApi.BY_COURSE_ID)
    public ResponseEntity<ResponseDto<Void>> deleteCourse(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long courseId) {
        ResponseDto<Void> data = courseService.deleteCourse(userPrincipal, courseId);
        return ResponseEntity.ok(data);
    }

}
