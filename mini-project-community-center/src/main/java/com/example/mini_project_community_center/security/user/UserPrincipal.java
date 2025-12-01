package com.example.mini_project_community_center.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;

@Getter
@ToString(exclude = "password")
public class UserPrincipal implements UserDetails, OAuth2User, Serializable {

    private String id;
    private final String loginId;

    @JsonIgnore
    private final String password;

    @JsonIgnore
    private final List<? extends GrantedAuthority> authorities;


}
