package com.example.mini_project_community_center.repository.report.impl;

import com.example.mini_project_community_center.common.enums.enrollment.EnrollmentsStatus;
import com.example.mini_project_community_center.dto.report.request.CourseReportRequest;
import com.example.mini_project_community_center.dto.report.response.CourseReportResponse;
import com.example.mini_project_community_center.repository.report.ReportRepositoryCustom;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.example.mini_project_community_center.entity.course.QCourse.course;
import static com.example.mini_project_community_center.entity.enrollment.QEnrollment.enrollment;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryCustomImpl implements ReportRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CourseReportResponse> getCourseReport(CourseReportRequest req) {

        List<CourseReportResponse> results = queryFactory
                .select(Projections.constructor(
                        CourseReportResponse.class,
                        course.id,
                        course.title,
                        course.capacity,
                        new CaseBuilder()
                                .when(enrollment.status.eq(EnrollmentsStatus.REFUNDED))
                                .then(1)
                                .otherwise(0)
                                .sum().intValue(),
                        Expressions.numberTemplate(
                                Double.class,
                                "CASE WHEN {0} > 0 THEN CAST({1} AS DOUBLE) / {0} * 100 ELSE 0 END",
                                course.capacity,
                                enrollment.id.count()
                        )
                ))
                .from(course)
                .leftJoin(enrollment).on(enrollment.course.id.eq(course.id)
                        .and(enrollment.status.ne(EnrollmentsStatus.CANCELED)))
                .where(
                        eqCenterId(req.centerId()),
                        betweenDate(req.from(), req.to())
                )
                .groupBy(course.id, course.title, course.capacity)
                .orderBy(getCourseSortExpression(req.sort()))
                .limit(req.limit() != null ? req.limit() : 100)
                .fetch();

        return results;
    }

    private BooleanExpression eqCenterId(Long centerId) {
        return centerId != null ? course.center.id.eq(centerId) : null;
    }

    private BooleanExpression betweenDate(String from, String to) {
        if (from != null && to != null) {
            LocalDate fromDate = LocalDate.parse(from);
            LocalDate toDate = LocalDate.parse(to);
            return course.startDate.goe(fromDate).and(course.endDate.loe(toDate));
        }
        return null;
    }

    private OrderSpecifier<?> getCourseSortExpression(String sort) {
        if (sort == null || sort.isBlank()) {
            return course.id.desc();
        }

        return switch (sort.toLowerCase()) {
            case "enrolled" -> enrollment.id.count().desc();
            case "rate" -> Expressions.numberTemplate(
                    Double.class,
                    "CASE WHEN {0} > 0 THEN CAST({1} AS DOUBLE) / {0} * 100 ELSE 0 END",
                    course.capacity,
                    enrollment.id.count()
            ).desc();
            default -> course.id.desc();
        };
    }
}
