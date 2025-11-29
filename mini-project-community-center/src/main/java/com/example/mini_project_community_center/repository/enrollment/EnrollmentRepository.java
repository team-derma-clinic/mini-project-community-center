package com.example.mini_project_community_center.repository.enrollment;

import com.example.mini_project_community_center.entity.enrollment.Enrollment;
import com.example.mini_project_community_center.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUserOrderByEnrolledAtDesc(User user);

    boolean existsByCourseId(Long courseId);

    @Query("""
            SELECT COUNT(e)
            FROM Enrollment e
            WHERE e.course.id = :courseId
                ANDE e.status = 'CONFIRMED'
            """)
    Long countConfirmedByCourseId(@Param("courseId") Long courseId);
}
