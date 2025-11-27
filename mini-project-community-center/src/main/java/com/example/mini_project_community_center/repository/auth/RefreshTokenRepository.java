package com.example.mini_project_community_center.repository.auth;

import com.example.mini_project_community_center.entity.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
