package com.example.mini_project_community_center.repository.user;

import com.example.mini_project_community_center.common.enums.user.AuthProvider;
import com.example.mini_project_community_center.common.enums.user.RoleStatus;
import com.example.mini_project_community_center.common.enums.user.RoleType;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.security.user.UserPrincipalMapper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    List<User> findUsersByRole(RoleType role);

    Optional<User> findByLoginId(@NonNull String loginId);

    Optional<User> findByProviderAndProviderId(AuthProvider provider, String providerId);

    Optional<User> findByEmail(@NotBlank(message = "이메일은 필수입니다.") @Email(message = "이메일 형식이 올바르지 않습니다.") String email);

    List<User> findAllByRoleStatus(RoleStatus roleStatus);
}
