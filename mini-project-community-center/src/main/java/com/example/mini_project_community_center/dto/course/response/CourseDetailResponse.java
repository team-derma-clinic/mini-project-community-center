package com.example.mini_project_community_center.dto.course.response;

import com.example.mini_project_community_center.common.enums.course.CourseCategory;
import com.example.mini_project_community_center.common.enums.course.CourseLevel;
import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.dto.user.response.UserListItemResponseDto;
import com.example.mini_project_community_center.entity.course.Course;

import java.util.List;


public record CourseDetailResponse(
        Long id,
        Long centerId,
        String title,
        CourseCategory category,
        CourseLevel level,
        String startDate,
        String endDate,
        List<UserListItemResponseDto> instructors
) {
    public static CourseDetailResponse fromEntity(Course course, List<UserListItemResponseDto> instructors) {
        return new CourseDetailResponse(
                course.getId(),
                course.getCenter().getId(),
                course.getTitle(),
                course.getCategory(),
                course.getLevel(),
                DateUtils.toKstDateString(course.getStartDate()),
                DateUtils.toKstDateString(course.getEndDate()),
                instructors
        );
    }
}
