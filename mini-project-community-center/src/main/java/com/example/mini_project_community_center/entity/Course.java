package com.example.mini_project_community_center.entity;

import com.example.mini_project_community_center.common.enums.CourseCategory;
import com.example.mini_project_community_center.common.enums.CourseLevel;
import com.example.mini_project_community_center.common.enums.CoursesStatus;
import com.example.mini_project_community_center.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses",
        indexes = {
                @Index(name = "idx_courses_center", columnList = "center_id, status, category, level"),
                @Index(name = "idx_courses_period", columnList = "start_date, end_date")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Course extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "center_id", foreignKey = @ForeignKey(name = "fk_course_center"), nullable = false)
    private Center center;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private CourseCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private CourseLevel level;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal fee = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CoursesStatus status = CoursesStatus.OPEN;

    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT", nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    public static Course createCourse(
            Center center,
            String title,
            CourseCategory category,
            CourseLevel level,
            int capacity,
            BigDecimal fee,
            CoursesStatus status,
            String description,
            LocalDate startDate,
            LocalDate endDate
    ) {
        Course course = new Course();
        course.center = center;
        course.title = title;
        course.category = category;
        course.level = level;
        course.capacity = capacity;
        course.fee = (fee != null ? fee: BigDecimal.ZERO);
        course.status = (status != null ? status : CoursesStatus.OPEN);
        course.description = description;
        course.startDate = startDate;
        course.endDate = endDate;

        return course;
    }

    public void update(
            String title,
            CourseCategory category,
            CourseLevel level,
            int capacity,
            BigDecimal fee,
            CoursesStatus status,
            String description,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.title = title;
        this.category = category;
        this.level = level;
        this.capacity = capacity;
        this.fee = (fee != null ? fee: BigDecimal.ZERO);
        this.status = (status != null ? status : CoursesStatus.OPEN);
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
