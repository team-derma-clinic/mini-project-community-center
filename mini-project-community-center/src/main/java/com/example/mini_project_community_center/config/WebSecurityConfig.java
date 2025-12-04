package com.example.mini_project_community_center.config;

import com.example.mini_project_community_center.security.filter.JwtAuthenticationFilter;
import com.example.mini_project_community_center.security.handler.JsonAccessDeniedHandler;
import com.example.mini_project_community_center.security.handler.JsonAuthenticationEntryPoint;
import com.example.mini_project_community_center.security.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.example.mini_project_community_center.security.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JsonAuthenticationEntryPoint authenticationEntryPoint;
    private final JsonAccessDeniedHandler accessDeniedHandler;

    @Value("${cors.allowed-origins:*}")
    private String allowedOrigins;

    @Value("${cors.allowed-headers:*}")
    private String allowedHeaders;

    @Value("${cors.allowed-methods:GET,POST,PUT,PATCH,DELETE,OPTIONS}")
    private String allowedMethods;

    @Value("${cors.exposed-headers:Authorization,Set-Cookie}")
    private String exposedHeaders;

    @Value("${security.h2-console:true}")
    private boolean h2ConsoleEnabled;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(splitToList(allowedOrigins));
        config.setAllowedHeaders(splitToList(allowedHeaders));
        config.setAllowedMethods(splitToList(allowedMethods));
        config.setExposedHeaders(splitToList(exposedHeaders));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CustomOAuth2UserService customOAuth2UserService,
            OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler
    ) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                );

        if (h2ConsoleEnabled) {
            http.headers(h -> h.frameOptions(f -> f.sameOrigin()));
        }

        http.authorizeHttpRequests(auth -> {

                    if (h2ConsoleEnabled) {
                        auth.requestMatchers("/h2-console/**").permitAll();
                    }

                    auth
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .requestMatchers(
                                    "/api/v1/auth/**",
                                    "/oauth2/**",
                                    "/login/oauth2/code/**",
                                    "/error").permitAll()

                            .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                            .requestMatchers("/api/v1/staffs/**").hasRole("STAFF")
                            .requestMatchers("/api/v1/instructors/**").hasRole("INSTRUCTOR")
                            .requestMatchers("/api/v1/students/**").hasRole("STUDENT")

                            .requestMatchers("/api/v1/users/me/**").authenticated()
                            .requestMatchers("/api/v1/users/**").hasAnyRole("ADMIN", "STAFF")

                            // 센터/강좌/세션
                            .requestMatchers(HttpMethod.POST,"/api/v1/centers/**").hasAnyRole("ADMIN", "STAFF")
                            .requestMatchers(HttpMethod.GET,"/api/v1/centers/**").permitAll()
                            .requestMatchers(HttpMethod.PUT,"/api/v1/centers/**").hasAnyRole("ADMIN", "STAFF")
                            .requestMatchers(HttpMethod.DELETE,"/api/v1/centers/**").hasRole("ADMIN")

                            .requestMatchers(HttpMethod.POST, "/api/v1/courses/**").hasAnyRole("ADMIN", "STAFF")
                            .requestMatchers(HttpMethod.GET, "/api/v1/courses/**").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/v1/courses/**").hasAnyRole("ADMIN", "STAFF")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/courses/**").hasAnyRole("ADMIN", "STAFF")

                            .requestMatchers(HttpMethod.POST, "/api/v1/sessions/**").hasAnyRole("ADMIN", "STAFF")
                            .requestMatchers(HttpMethod.GET, "/api/v1/sessions/**").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/v1/sessions/**").hasAnyRole("ADMIN", "STAFF")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/sessions/**").hasAnyRole("ADMIN", "STAFF")

                            // 등록/결제/환불
                            .requestMatchers(HttpMethod.POST,"/api/v1/enrollments/**").hasRole("STUDENT")
                            .requestMatchers(HttpMethod.GET,"/api/v1/enrollments/me/**").hasRole("STUDENT")

                            .requestMatchers(HttpMethod.GET,"/api/v1/enrollments/*").authenticated() // 상세조회 - 순서 건드리지말것

                            .requestMatchers(HttpMethod.GET,"/api/v1/enrollments/**").hasAnyRole("ADMIN", "STAFF") // 전체조회

                            .requestMatchers(HttpMethod.PUT,"/api/v1/enrollments/*/cancel").hasAnyRole("ADMIN", "STAFF", "STUDENT")
                            .requestMatchers(HttpMethod.PUT,"/api/v1/enrollments/*/refund").hasAnyRole("ADMIN", "STAFF")
                            .requestMatchers(HttpMethod.PUT,"/api/v1/enrollments/*/status").hasAnyRole("ADMIN", "STAFF")

                            .requestMatchers(HttpMethod.POST, "/api/v1/payments/**").hasAnyRole("ADMIN", "STAFF", "STUDENT")
                            .requestMatchers(HttpMethod.GET, "/api/v1/payments/**").hasAnyRole("ADMIN", "STAFF", "STUDENT")
                            .requestMatchers(HttpMethod.PUT, "/api/v1/payments/*/refund").hasAnyRole("ADMIN", "STAFF")

                            // 출석
                            .requestMatchers(HttpMethod.POST,"/api/v1/attendance/**").hasAnyRole("STAFF", "INSTRUCTOR")
                            .requestMatchers(HttpMethod.PUT,"/api/v1/attendance/**").hasAnyRole("STAFF", "INSTRUCTOR")
                            .requestMatchers(HttpMethod.GET,"/api/v1/courses/*/attendance").hasAnyRole("ADMIN","STAFF", "INSTRUCTOR")
                            .requestMatchers(HttpMethod.GET,"/api/v1/sessions/*/attendance").hasAnyRole("ADMIN","STAFF", "INSTRUCTOR")

                            .requestMatchers(HttpMethod.POST,"/api/v1/reviews/**").hasRole("STUDENT")
                            .requestMatchers(HttpMethod.GET,"/api/v1/reviews/me/**").hasRole("STUDENT")
                            .requestMatchers(HttpMethod.GET,"/api/v1/courses/*/reviews/**").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/reviews/**").hasAnyRole("ADMIN", "STAFF")

                            //통계
                            .requestMatchers("/api/v1/reports/**").hasAnyRole("ADMIN", "STAFF")

                            .anyRequest().authenticated(); // 그 외에는 인증 필요
                })
                // OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userinfo ->
                                userinfo.userService(customOAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private List<String> splitToList(String csv) {
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }
}
