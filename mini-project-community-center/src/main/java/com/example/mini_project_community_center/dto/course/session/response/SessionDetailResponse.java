package com.example.mini_project_community_center.dto.course.session.response;

import com.example.mini_project_community_center.common.enums.course.CourseSessionsStatus;
import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.entity.course.session.CourseSession;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SessionDetailResponse (
        Long id,
        Long courseId,
        String startTime,
        String endTime,
        String room,
        CourseSessionsStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static SessionDetailResponse fromEntity(CourseSession session) {
        return new SessionDetailResponse(
                session.getId(),
                session.getCourse().getId(),
                DateUtils.toKstString(session.getStartTime()),
                DateUtils.toKstString(session.getEndTime()),
                session.getRoom(),
                session.getStatus(),
                session.getCreatedAt(),
                session.getUpdatedAt()
        );
    }
}
