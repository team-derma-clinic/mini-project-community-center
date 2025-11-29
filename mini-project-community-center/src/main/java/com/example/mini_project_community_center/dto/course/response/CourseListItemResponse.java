package com.example.mini_project_community_center.dto.course.response;

import com.example.mini_project_community_center.common.enums.course.CourseCategory;
import com.example.mini_project_community_center.common.enums.course.CourseLevel;
import com.example.mini_project_community_center.common.enums.course.CourseStatus;
import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.entity.course.Course;

import java.util.List;

public record CourseListItemResponse (
        Long id,
        Long centerId,
        String title,
        CourseCategory category,
        CourseLevel level,
        String startDate,
        String endDate,
        Integer capacity,
        Integer currentEnrollment,
        CourseStatus status
) {
    public static CourseListItemResponse fromEntity(Course course, Integer currentEnrollment) {
        return new CourseListItemResponse(
                course.getId(),
                course.getCenter().getId(),
                course.getTitle(),
                course.getCategory(),
                course.getLevel(),
                DateUtils.toKstDateString(course.getStartDate()),
                DateUtils.toKstDateString(course.getEndDate()),
                course.getCapacity(),
                currentEnrollment == null ? 0 : currentEnrollment,
                course.getStatus()
        );
    }

    public static List<CourseListItemResponse> from(List<Course> courses, Integer currentEnrollment) {
        return courses.stream()
                .map(course -> CourseListItemResponse.fromEntity(course, currentEnrollment))
                .toList();
    }
}
