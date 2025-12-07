package com.example.mini_project_community_center.repository.course.course.impl;

import com.example.mini_project_community_center.common.enums.course.CourseCategory;
import com.example.mini_project_community_center.common.enums.course.CourseLevel;
import com.example.mini_project_community_center.common.enums.course.CourseStatus;
import com.example.mini_project_community_center.dto.course.request.CourseSearchRequest;
import com.example.mini_project_community_center.entity.course.Course;
import com.example.mini_project_community_center.entity.course.session.QCourseSession;
import com.example.mini_project_community_center.repository.course.course.CourseRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.example.mini_project_community_center.entity.course.QCourse.course;
import static com.example.mini_project_community_center.entity.course.QCourseInstructor.courseInstructor;

@Repository
@RequiredArgsConstructor
public class CourseRepositoryCustomImpl implements CourseRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QCourseSession session = QCourseSession.courseSession;

    @Override
    public Page<Course> searchCourses(CourseSearchRequest req, Pageable pageable) {
        List<Course> content = queryFactory
                .selectFrom(course)
                .leftJoin(course.sessions, session)
                .where(
                        eqCenterId(req.centerId()),
                        eqCategory(req.category()),
                        eqLevel(req.level()),
                        eqStatus(req.status()),
                        betweenDate(req.from(), req.to()),
                        eqWeekday(req.weekday()),
                        betweenTime(req.timeRange()),
                        containsKeyword(req.q())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(course.createdAt.desc())
                .fetch();

        Long total = queryFactory
                .select(course.count())
                .from(course)
                .leftJoin(course.sessions, session)
                .where(
                        eqCenterId(req.centerId()),
                        eqCategory(req.category()),
                        eqLevel(req.level()),
                        eqStatus(req.status()),
                        betweenDate(req.from(), req.to()),
                        eqWeekday(req.weekday()),
                        betweenTime(req.timeRange()),
                        containsKeyword(req.q())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    private BooleanExpression eqCenterId(Long centerId) {
        return centerId != null ? course.center.id.eq(centerId) : null;
    }

    private BooleanExpression eqCategory(CourseCategory category) {
        return category != null ? course.category.eq(category) : null;
    }

    private BooleanExpression eqLevel(CourseLevel level) {
        return level != null ? course.level.eq(level) : null;
    }

    private BooleanExpression eqStatus(CourseStatus status) {
        return status != null ? course.status.eq(status) : null;
    }

    private BooleanExpression betweenDate(String from, String to) {
        BooleanExpression predicate = null;

        if (from != null && !from.isBlank()) {
            predicate = course.startDate.goe(LocalDate.parse(from));
        }
        if (to != null && !to.isBlank()) {
            BooleanExpression toExpr = course.endDate.loe(LocalDate.parse(to));
            predicate = (predicate == null) ? toExpr : predicate.and(toExpr);
        }
        return predicate;
    }

    private BooleanExpression eqWeekday(Integer weekday) {
        return weekday != null ? session.startTime.dayOfWeek().eq(weekday) : null;
    }

    private BooleanExpression betweenTime(String timeRange) {
        if (timeRange == null || timeRange.isBlank()) return null;

        String[] parts = timeRange.split("-");
        if (parts.length != 2) return null;

        LocalTime startCondition;
        LocalTime endCondition;

        try {
            startCondition = LocalTime.parse(parts[0]);
            endCondition = LocalTime.parse(parts[1]);
        } catch (Exception e) {
            return null;
        }

        return Expressions.stringTemplate(
                        "DATE_FORMAT({0}, '%H:%i')",
                        session.startTime
                ).goe(startCondition.toString())
                .and(
                        Expressions.stringTemplate(
                                "DATE_FORMAT({0}, '%H:%i')",
                                session.endTime
                        ).loe(endCondition.toString())
                );

    }

    private BooleanExpression containsKeyword(String q) {
        return q != null && !q.isBlank()
                ? course.title.containsIgnoreCase(q)
                : null;
    }

    @Override
    public List<Course> findByInstructorId(Long instructorId) {
        return queryFactory
                .selectFrom(course)
                .join(course.instructors, courseInstructor)
                .where(courseInstructor.instructor.id.eq(instructorId))
                .distinct()
                .fetch();
    }
}
