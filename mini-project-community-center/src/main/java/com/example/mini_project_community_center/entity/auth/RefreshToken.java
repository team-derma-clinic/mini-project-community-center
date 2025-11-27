package com.example.mini_project_community_center.entity.auth;

import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(
        name = "refresh_tokens",
        indexes = {
                @Index(name = "idx_refresh_token_user_id", columnList = "user_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            unique = true,
            foreignKey = @ForeignKey(name = "fk_refresh_token_user")
    )
    private User user;

    @Column(nullable = false, length = 512)
    private String token;

    @Column(nullable = false)
    private Instant expiry;

    @Builder
    private RefreshToken(User user, String token, Instant expiry) {
        this.user = user;
        this.token = token;
        this.expiry = expiry;
    }

    public void renew (String newToken, Instant newExpiry) {
        this.token = newToken;
        this.expiry = newExpiry;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiry); // 시간의 경과흐름을 파악하는 Instant
    }
}