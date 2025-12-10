package com.example.mini_project_community_center.repository.course.session.impl;

import com.example.mini_project_community_center.dto.course.session.request.SessionSearchRequest;
import com.example.mini_project_community_center.entity.course.session.CourseSession;
import com.example.mini_project_community_center.repository.course.session.CourseSessionRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.*;
import java.util.List;

import static com.example.mini_project_community_center.entity.course.QCourse.course;
import static com.example.mini_project_community_center.entity.course.session.QCourseSession.courseSession;

@Repository
@RequiredArgsConstructor
public class CourseSessionRepositoryCustomImpl implements CourseSessionRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CourseSession> searchSessions(Long courseId, SessionSearchRequest req, Pageable pageable) {
        BooleanExpression predicate = courseSession.course.id.eq(courseId)
                .and(betweenDate(req.from(), req.to()))
                .and(eqWeekday(req.weekday()))
                .and(betweenTime(req.timeRange()))
                .and(containsKeyword(req.q()));

        List<CourseSession> content = queryFactory
                .selectFrom(courseSession)
                .join(courseSession.course, course)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(courseSession.createdAt.desc())
                .fetch();

        Long total = queryFactory
                .select(courseSession.count())
                .from(courseSession)
                .join(courseSession.course, course)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    private BooleanExpression betweenDate(String from, String to) {
        BooleanExpression predicate = null;

        if (from != null && !from.isBlank()) {
            LocalDateTime fromKst = LocalDate.parse(from).atStartOfDay();
            predicate = courseSession.startTime.goe(fromKst);
        }

        if (to != null && !to.isBlank()) {
            LocalDateTime toKst = LocalDate.parse(to).atTime(23, 59, 59);
            BooleanExpression toExpr = courseSession.startTime.loe(toKst);

            predicate = (predicate == null) ? toExpr : predicate.and(toExpr);
        }
        return predicate;
    }

    private BooleanExpression eqWeekday(Integer weekday) {
        return weekday != null ? courseSession.startTime.dayOfWeek().eq(weekday) : null;
    }

    private BooleanExpression betweenTime(String timeRange) {
        if (timeRange == null || timeRange.isBlank()) return null;

        String[] parts = timeRange.split("-");
        if (parts.length != 2) return null;

        try {
            LocalTime fromTime = LocalTime.parse(parts[0]);
            LocalTime toTime = LocalTime.parse(parts[1]);

            return courseSession.startTime.hour().goe(fromTime.getHour())
                    .and(courseSession.startTime.minute().goe(fromTime.getMinute()))
                    .and(courseSession.endTime.hour().loe(toTime.getHour()))
                    .and(courseSession.endTime.minute().loe(toTime.getMinute()));
        } catch (Exception e) {
            return null;
        }
    }

    private BooleanExpression containsKeyword(String q) {
        return q != null && !q.isBlank()
                ? courseSession.course.title.containsIgnoreCase(q)
                : null;
    }
}
