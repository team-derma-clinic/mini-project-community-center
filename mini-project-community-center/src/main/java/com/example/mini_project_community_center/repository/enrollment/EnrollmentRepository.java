package com.example.mini_project_community_center.repository.enrollment;

import com.example.mini_project_community_center.common.enums.enrollment.EnrollmentsStatus;
import com.example.mini_project_community_center.entity.enrollment.Enrollment;
import com.example.mini_project_community_center.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Page<Enrollment> findByUserOrderByEnrolledAtDesc(User user, Pageable pageable);

    Page<Enrollment> findByStatus(EnrollmentsStatus status, Pageable pageable);

    boolean existsByCourseIdAndUserId(Long courseId, Long userId);

    boolean existsByCourseId(Long courseId);

    @Query("""
            SELECT COUNT(e)
            FROM Enrollment e
            WHERE e.course.id = :courseId
                AND e.status = 'CONFIRMED'
            """)
    Long countConfirmedByCourseId(@Param("courseId") Long courseId);
}
