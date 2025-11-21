package com.example.mini_project_community_center.dto.course.response;

import com.example.mini_project_community_center.common.enums.CourseCategory;
import com.example.mini_project_community_center.common.enums.CourseLevel;
import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.entity.Course;


public record CourseDetailResponse(
        Long id,
        Long centerId,
        String title,
        CourseCategory category,
        CourseLevel level,
        String startDate,
        String endDate
) {
    public static CourseDetailResponse fromEntity(Course course) {
        return new CourseDetailResponse(
                course.getId(),
                course.getCenter().getId(),
                course.getTitle(),
                course.getCategory(),
                course.getLevel(),
                DateUtils.toKstDateString(course.getStartDate()),
                DateUtils.toKstDateString(course.getEndDate())
        );
    }
}
