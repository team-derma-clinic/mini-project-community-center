package com.example.mini_project_community_center.repository.course.session.impl;

import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.dto.course.session.request.SessionSearchRequest;
import com.example.mini_project_community_center.entity.course.session.CourseSession;
import com.example.mini_project_community_center.repository.course.session.CourseSessionRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
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
            LocalDateTime fromUTC = LocalDate.parse(from)
                    .atStartOfDay()
                    .atZone(ZoneId.of("Asia/Seoul"))
                    .withZoneSameInstant(ZoneId.of("UTC"))
                    .toLocalDateTime();

            predicate = courseSession.startTime.goe(fromUTC);
        }
        if (to != null && !to.isBlank()) {
            LocalDateTime toUTC = LocalDate.parse(to)
                    .atTime(23, 59, 59)
                    .atZone(ZoneId.of("Asia/Seoul"))
                    .withZoneSameInstant(ZoneId.of("UTC"))
                    .toLocalDateTime();

            BooleanExpression toExpr = courseSession.startTime.loe(toUTC);
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
            LocalTime startKstTime = LocalTime.parse(parts[0]);
            LocalTime endKstTime = LocalTime.parse(parts[1]);

            LocalTime startUtc = DateUtils.convertKstToUtc(startKstTime);
            LocalTime endUtc = DateUtils.convertKstToUtc(endKstTime);

            System.out.println("startUtc: " + startUtc);
            System.out.println("endUtc: " + endUtc);

            return Expressions.stringTemplate("TIME({0})", courseSession.startTime)
                    .goe(startUtc.toString())
                    .and(
                            Expressions.stringTemplate("TIME({0})", courseSession.endTime)
                                    .loe(endUtc.toString())
                    );

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
