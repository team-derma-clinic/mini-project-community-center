package com.example.mini_project_community_center.entity.user;

import com.example.mini_project_community_center.common.enums.user.AuthProvider;
import com.example.mini_project_community_center.common.enums.user.RoleStatus;
import com.example.mini_project_community_center.common.enums.user.RoleType;
import com.example.mini_project_community_center.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_login", columnNames = "login_id"),
                @UniqueConstraint(name = "uk_users_email", columnNames = "email")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "login_id", updatable = false, nullable = false, length = 50)
    private String loginId;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "phone", length = 30)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private RoleType role;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_status", nullable = false, length = 30)
    private RoleStatus roleStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", length = 20, nullable = false)
    private AuthProvider provider;

    @Column(name = "provider_id", length = 100)
    private String providerId;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified;

    @Builder
    private User(
            String name,
            String loginId,
            String password,
            String email,
            String phone,
            RoleType role,
            RoleStatus roleStatus,
            AuthProvider provider,
            String providerId,
            boolean emailVerified
    ) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.roleStatus = roleStatus;
        this.provider = provider;
        this.providerId = providerId;
        this.emailVerified = emailVerified;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateUserInfo(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void changeRole(RoleType role) { this.role = role; }

    public void changeRoleStatus(RoleStatus newStatus) {this.roleStatus = newStatus;}

    public static User createOauthUser(
            AuthProvider provider,
            String providerId,
            String email,
            String name,
            RoleType role,
            RoleStatus roleStatue
    ) {
        return User.builder()
                .loginId(provider.name() + "_" + providerId)
                .password(null)
                .email(email)
                .name(name)
                .role(role)
                .roleStatus(roleStatue)
                .provider(provider)
                .providerId(providerId)
                .emailVerified(true)
                .build();
    }

    public void updateOauthProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
