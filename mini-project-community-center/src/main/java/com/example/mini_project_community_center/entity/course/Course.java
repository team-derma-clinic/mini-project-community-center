package com.example.mini_project_community_center.entity.course;

import com.example.mini_project_community_center.common.enums.course.CourseCategory;
import com.example.mini_project_community_center.common.enums.course.CourseLevel;
import com.example.mini_project_community_center.common.enums.course.CourseStatus;
import com.example.mini_project_community_center.entity.course.session.CourseSession;
import com.example.mini_project_community_center.entity.file.FileInfo;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.entity.base.BaseTimeEntity;
import com.example.mini_project_community_center.entity.center.Center;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseSession> sessions = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseInstructor> instructors = new ArrayList<>();

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 50)
    private CourseCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false, length = 20)
    private CourseLevel level;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal fee = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private CourseStatus status = CourseStatus.OPEN;

    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT", nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "thumbnail_id")
    private Long thumbnailId;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseFile> files = new ArrayList<>();
    

    public static Course create(
            Center center,
            String title,
            CourseCategory category,
            CourseLevel level,
            int capacity,
            BigDecimal fee,
            CourseStatus status,
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
        course.fee = (fee != null ? fee : BigDecimal.ZERO);
        course.status = (status != null ? status : CourseStatus.OPEN);
        course.description = description;
        course.startDate = startDate;
        course.endDate = endDate;

        course.validateDate();

        return course;
    }

    public void updateCenter(
            String title,
            CourseCategory category,
            CourseLevel level,
            int capacity,
            BigDecimal fee,
            CourseStatus status,
            String description,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.title = title;
        this.category = category;
        this.level = level;
        this.capacity = capacity;
        this.fee = (fee != null ? fee : BigDecimal.ZERO);
        this.status = (status != null ? status : CourseStatus.OPEN);
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;

        this.validateDate();
    }

    public void addInstructor(User instructor) {
        CourseInstructor courseInstructor = CourseInstructor.create(this, instructor);
        this.instructors.add(courseInstructor);
    }

    public void removeInstructor(User instructor) {
        this.instructors.removeIf(i -> i.getInstructor().getId().equals(instructor.getId()));
    }

    public void updateInstructors(List<User> newInstructors) {
        this.instructors.clear();
        newInstructors.forEach(this::addInstructor);
    }

    private void validateDate() {
        if(this.startDate.isAfter(this.endDate)) {
            throw new IllegalArgumentException("시작 날짜는 종료 날짜보다 이전이어야 합니다.");
        }
    }

    public void updateStatus(CourseStatus status) {
        this.status = (status != null) ? status : CourseStatus.OPEN;
    }

    public void updateThumbnail(Long fileInfoId) {
        this.thumbnailId = fileInfoId;
    }
}
