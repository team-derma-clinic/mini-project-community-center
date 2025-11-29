package com.example.mini_project_community_center.dto.course.response;

import com.example.mini_project_community_center.common.enums.course.CourseCategory;
import com.example.mini_project_community_center.common.enums.course.CourseLevel;
import com.example.mini_project_community_center.common.enums.course.CourseStatus;
import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.dto.user.response.UserListItemResponse;
import com.example.mini_project_community_center.entity.course.Course;

import java.math.BigDecimal;
import java.util.List;


public record CourseDetailResponse(
        Long id,
        Long centerId,
        String title,
        CourseCategory category,
        CourseLevel level,
        Integer capacity,
        Integer currentEnrollment,
        BigDecimal fee,
        CourseStatus status,
        String description,
        List<UserListItemResponse> instructors,
        String startDate,
        String endDate,
        String createdAt,
        String updatedAt
) {
    public static CourseDetailResponse fromEntity(Course course, List<UserListItemResponse> instructors, Integer currentEnrollment) {
        return new CourseDetailResponse(
                course.getId(),
                course.getCenter().getId(),
                course.getTitle(),
                course.getCategory(),
                course.getLevel(),
                course.getCapacity(),
                currentEnrollment == null ? 0 : currentEnrollment,
                course.getFee(),
                course.getStatus(),
                course.getDescription(),
                instructors,
                DateUtils.toKstDateString(course.getStartDate()),
                DateUtils.toKstDateString(course.getEndDate()),
                DateUtils.toKstString(course.getCreatedAt()),
                DateUtils.toKstString(course.getUpdatedAt())
        );
    }
}
