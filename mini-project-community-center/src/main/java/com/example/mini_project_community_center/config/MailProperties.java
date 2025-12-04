package com.example.mini_project_community_center.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {
    // 메일 설정값을 담는 자바 객체
    private String host;
    private int port;
    private String username;
    private String password;

    @org.springframework.beans.factory.annotation.Value("${app.mail.from}")
    private String from;
}
