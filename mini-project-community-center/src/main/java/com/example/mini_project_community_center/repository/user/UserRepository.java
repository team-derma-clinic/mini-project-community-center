package com.example.mini_project_community_center.repository.user;

import com.example.mini_project_community_center.common.enums.user.AuthProvider;
import com.example.mini_project_community_center.common.enums.user.RoleType;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.security.user.UserPrincipalMapper;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    List<User> findUserByRole(RoleType role);

    Optional<User> findByLoginId(@NonNull String loginId);

    Optional<User> findByProviderAndProviderId(AuthProvider provider, String providerId);
}
