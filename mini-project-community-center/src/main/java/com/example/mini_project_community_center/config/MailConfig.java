package com.example.mini_project_community_center.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MailConfig {
    // 스프링에서 이메일보내기(SMTP전송)를 위한 설정파일
    // 이메일 보내기 기능을 사용하려면 어떻게 연결하고 인증할지를 스프링에 알려주는 역할
    // 이메일 서버(SMTP)와 연결해서, 서비스를 통해 이메일을 보낼 수 있는 JavaMailSender객체를 만들어주는 설정 클래스
    private final MailProperties mailProperties;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp"); // smtp 프로토콜 사용
        props.put("mail.smtp.auth", "true"); // 로그인 인증 사용
        props.put("mail.smtp.starttls.enable", "true"); // STARTTLS 보안 연결
        props.put("mail.debug", "true"); // 콘솔에 이메일 로그 찍힘 (문제 디버깅용)

        return mailSender;
    }
}
