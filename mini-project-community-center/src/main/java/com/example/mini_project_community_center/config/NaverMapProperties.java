package com.example.mini_project_community_center.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "naver.map")
public class NaverMapProperties {
    private String clientId;
    private String clientSecret;
}
