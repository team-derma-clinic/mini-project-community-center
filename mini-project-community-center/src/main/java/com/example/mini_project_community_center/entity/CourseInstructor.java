package com.example.mini_project_community_center.entity;

import com.example.mini_project_community_center.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_instructors",
        uniqueConstraints = {
             @UniqueConstraint(name = "uk_course_instructor", columnNames = {"course_id, instructor_id"})
        },
        indexes = {
                @Index(name = "idx_course_instructor_course", columnList = "course_id"),
                @Index(name = "idx_course_instructor_instructor", columnList = "instructor_id")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CourseInstructor extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_course_instructor_course"))
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "instructor_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_course_instructor_instructor"))
    private User instructor;

    public static CourseInstructor create(Course course, User instructor) {
        CourseInstructor courseInstructor = new CourseInstructor();
        courseInstructor.course = course;
        courseInstructor.instructor = instructor;
        return courseInstructor;
    }
}
