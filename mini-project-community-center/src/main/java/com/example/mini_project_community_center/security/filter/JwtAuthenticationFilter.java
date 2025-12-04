package com.example.mini_project_community_center.security.filter;

import com.example.mini_project_community_center.security.provider.JwtProvider;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.security.user.UserPrincipalMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserPrincipalMapper userPrincipalMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = resolveToken(request);

        try {
            if (token != null && jwtProvider.isValidToken(token)) {

                String loginId = jwtProvider.getLoginIdFromJwt(token);
                UserPrincipal principal = userPrincipalMapper.toPrincipal(loginId);

                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(
                                principal, null, principal.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch(Exception ex) {
            log.warn("[JwtAuthenticationFilter] 토큰 검증 실패: {}", ex.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (StringUtils.hasText(header) && header.startsWith(JwtProvider.BEARER_PREFIX)) {
            return header.substring(JwtProvider.BEARER_PREFIX.length()).trim();
        }
        return null;
    }
}
