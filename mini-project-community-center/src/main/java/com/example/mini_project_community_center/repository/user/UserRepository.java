package com.example.mini_project_community_center.repository.user;

import com.example.mini_project_community_center.common.enums.RoleType;
import com.example.mini_project_community_center.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    List<User> findUserByRole(RoleType role);
}
