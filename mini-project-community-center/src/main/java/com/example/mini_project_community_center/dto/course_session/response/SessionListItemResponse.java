package com.example.mini_project_community_center.dto.course_session.response;

import com.example.mini_project_community_center.common.enums.course.CourseSessionsStatus;
import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.entity.course.CourseSession;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SessionListItemResponse(
        Long id,
        Long courseId,
        String startTime,
        String endTime,
        String room,
        CourseSessionsStatus status
) {
    public static SessionListItemResponse fromEntity(CourseSession session) {
        return new SessionListItemResponse(
                session.getId(),
                session.getCourse().getId(),
                DateUtils.toKstString(session.getStartTime()),
                DateUtils.toKstString(session.getEndTime()),
                session.getRoom(),
                session.getStatus()
        );
    }

    public static List<SessionListItemResponse> from(List<CourseSession> sessions) {
        return sessions.stream()
                .map(SessionListItemResponse::fromEntity)
                .toList();
    }
}
