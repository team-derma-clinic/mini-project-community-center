package com.example.mini_project_community_center.repository.course.session.impl;

import com.example.mini_project_community_center.common.utils.DateUtils;
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

import java.time.LocalTime;
import java.util.List;

import static com.example.mini_project_community_center.entity.course.QCourse.course;
import static com.example.mini_project_community_center.entity.course.session.QCourseSession.courseSession;

@Repository
@RequiredArgsConstructor
public class CourseSessionRepositoryCustomImpl implements CourseSessionRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CourseSession> searchSessions(SessionSearchRequest req, Pageable pageable) {
        List<CourseSession> content = queryFactory
                .selectFrom(courseSession)
                .join(courseSession.course, course)
                .where(
                        eqCourseId(req.courseId()),
                        betweenDate(req.from(), req.to()),
                        eqWeekday(req.weekday()),
                        betweenTime(req.timeRange()),
                        containsKeyword(req.q())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(courseSession.createdAt.desc())
                .fetch();

        Long total = queryFactory
                .select(courseSession.count())
                .from(courseSession)
                .join(courseSession.course, course)
                .where(
                        eqCourseId(req.courseId()),
                        betweenDate(req.from(), req.to()),
                        eqWeekday(req.weekday()),
                        betweenTime(req.timeRange()),
                        containsKeyword(req.q())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    private BooleanExpression eqCourseId(Long courseId) {
        return courseId != null ? courseSession.course.id.eq(courseId) : null;
    }

    private BooleanExpression betweenDate(String from, String to) {
        if(from != null && to != null) {
            return courseSession.startTime.goe(DateUtils.parseLocalDateTime(from))
                    .and(courseSession.endTime.loe(DateUtils.parseLocalDateTime(to)));
        }
        return null;
    }

    private BooleanExpression eqWeekday(Integer weekday) {
        return weekday != null ? courseSession.startTime.dayOfWeek().eq(weekday) : null;
    }

    private BooleanExpression betweenTime(String timeRange) {
        if(timeRange == null || timeRange.isBlank()) return null;

        String[] parts = timeRange.split("-");
        if(parts.length != 2) return null;

        LocalTime startCondition;
        LocalTime endCondition;

        try{
            startCondition = LocalTime.parse(parts[0]);
            endCondition = LocalTime.parse(parts[1]);
        } catch (Exception e) {
            return null;
        }

        return Expressions.stringTemplate(
                        "DATE_FORMAT({0}, '%H:%i')",
                        courseSession.startTime
                ).goe(startCondition.toString())
                .and(
                        Expressions.stringTemplate(
                        "DATE_FORMAT({0}, '%H:%i')",
                        courseSession.endTime
                        ).loe(endCondition.toString())
                );
    }

    private BooleanExpression containsKeyword(String q) {
        return q != null && !q.isBlank()
                ? courseSession.course.title.containsIgnoreCase(q)
                : null;
    }
}
