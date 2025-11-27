package com.example.mini_project_community_center.repository.center;

import com.example.mini_project_community_center.entity.center.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {
    List<Center> findByNameContaining(String keyword);
}
