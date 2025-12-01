package com.example.mini_project_community_center.repository.auth;

import com.example.mini_project_community_center.entity.auth.RefreshToken;
import com.example.mini_project_community_center.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUser(User user);
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
