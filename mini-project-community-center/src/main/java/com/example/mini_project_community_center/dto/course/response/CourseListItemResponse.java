package com.example.mini_project_community_center.dto.course.response;

import com.example.mini_project_community_center.common.enums.CourseCategory;
import com.example.mini_project_community_center.common.enums.CourseLevel;
import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.entity.Course;

import java.util.List;

public record CourseListItemResponse (
        Long id,
        Long centerId,
        String title,
        CourseCategory category,
        CourseLevel level,
        String startDate,
        String endDate
) {
    public static CourseListItemResponse fromEntity(Course course) {
        return new CourseListItemResponse(
                course.getId(),
                course.getCenter().getId(),
                course.getTitle(),
                course.getCategory(),
                course.getLevel(),
                DateUtils.toKstDateString(course.getStartDate()),
                DateUtils.toKstDateString(course.getEndDate())
        );
    }

    public static List<CourseListItemResponse> from(List<Course> courses) {
        return courses.stream()
                .map(CourseListItemResponse::fromEntity)
                .toList();
    }
}
