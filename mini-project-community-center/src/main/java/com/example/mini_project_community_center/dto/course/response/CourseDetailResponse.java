package com.example.mini_project_community_center.dto.course.response;

import com.example.mini_project_community_center.common.enums.CourseCategory;
import com.example.mini_project_community_center.common.enums.CourseLevel;


public record CourseDetailResponse(
        Long id,
        Long centerId,
        String title,
        CourseCategory category,
        CourseLevel level,
        String startDate,
        String endDate
) { }
