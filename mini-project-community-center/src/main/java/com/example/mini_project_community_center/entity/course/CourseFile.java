package com.example.mini_project_community_center.entity.course;

import com.example.mini_project_community_center.entity.file.FileInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_files")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseFile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_course_files_course"))
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "file_id", foreignKey = @ForeignKey(name = "fk_course_files_file_info"))
    private FileInfo fileInfo;

    @Column(name = "display_order")
    private Integer displayOrder;

    private CourseFile(Course course, FileInfo fileInfo, Integer displayOrder) {
        this.course = course;
        this.fileInfo = fileInfo;
    }

    public static CourseFile of(Course course, FileInfo fileInfo, Integer displayOrder) {
        if (displayOrder == null) { displayOrder = 0;}
        return new CourseFile(course, fileInfo, displayOrder);
    }
}
