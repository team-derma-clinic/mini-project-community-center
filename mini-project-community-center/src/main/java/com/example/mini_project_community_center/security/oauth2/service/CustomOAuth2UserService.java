package com.example.mini_project_community_center.security.oauth2.service;

import com.example.mini_project_community_center.common.enums.user.AuthProvider;
import com.example.mini_project_community_center.common.enums.user.RoleStatus;
import com.example.mini_project_community_center.common.enums.user.RoleType;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.repository.user.UserRepository;
import com.example.mini_project_community_center.security.oauth2.user.GoogleOAuth2UserInfo;
import com.example.mini_project_community_center.security.oauth2.user.KakaoOAuth2UserInfo;
import com.example.mini_project_community_center.security.oauth2.user.NaverOAuth2UserInfo;
import com.example.mini_project_community_center.security.oauth2.user.OAuth2UserInfo;
import com.example.mini_project_community_center.security.user.UserPrincipalMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final UserPrincipalMapper userPrincipalMapper;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        AuthProvider provider = mapProvider(registrationId);

        OAuth2UserInfo userInfo = convertToUserInfo(provider, oAuth2User.getAttributes());

        User user = upsertUser(provider, userInfo);

        return userPrincipalMapper.toPrincipal(user.getLoginId());
    }

    private AuthProvider mapProvider(String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> AuthProvider.GOOGLE;
            case "kakao" -> AuthProvider.KAKAO;
            case "naver" -> AuthProvider.NAVER;
            default -> throw new IllegalArgumentException("지원하지 않는 provider: " + registrationId);
        };
    }

    private OAuth2UserInfo convertToUserInfo(AuthProvider provider, Map<String, Object> attributes) {
        return switch (provider) {
            case GOOGLE -> new GoogleOAuth2UserInfo(attributes);
            case KAKAO -> new KakaoOAuth2UserInfo(attributes);
            case NAVER -> new NaverOAuth2UserInfo(attributes);
            default -> throw new IllegalArgumentException("지원하지 않는 provider: " + provider);
        };
    }

    @Transactional
    protected User upsertUser(AuthProvider provider, OAuth2UserInfo userInfo) {

        String providerId = userInfo.getId();
        String email = userInfo.getEmail();
        String name = userInfo.getName();

        return userRepository.findByProviderAndProviderId(provider, providerId)
                .map(user -> {
                    user.updateOauthProfile(name, email);
                    return user;
                })
                .orElseGet(() -> {
                    User newUser = User.createOauthUser(
                            provider,
                            providerId,
                            email,
                            name,
                            RoleType.STUDENT,
                            RoleStatus.APPROVED
                    );

                    return userRepository.save(newUser);
                });
    }
}
