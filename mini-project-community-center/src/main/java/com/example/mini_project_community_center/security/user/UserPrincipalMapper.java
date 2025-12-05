package com.example.mini_project_community_center.security.user;

import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.repository.user.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserPrincipalMapper {

    private final UserRepository userRepository;

    public UserPrincipal toPrincipal(@NonNull String loginId) {

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, "해당 사용자가 없습니다: " + loginId));

        return map(user);
    }


    public UserPrincipal map(@NonNull User user) {
        String roleName = (user.getRole() == null)
                ? "STUDENT"
                : user.getRole().name();

        List<SimpleGrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName));

        return UserPrincipal.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .authorities(authorities)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }
}
