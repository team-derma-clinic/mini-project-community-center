package com.example.mini_project_community_center.repository.center;

import com.example.mini_project_community_center.entity.center.Center;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {

    @Query("SELECT c FROM Center c WHERE c.isActive = true")
    Page<Center> findAllActive(Pageable pageable);

    @Query("""
            SELECT c
            FROM Center c
            WHERE c.isActive = true
                AND LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Center> searchActiveByName(@Param("keyword") String keyword, Pageable pageable);
}
