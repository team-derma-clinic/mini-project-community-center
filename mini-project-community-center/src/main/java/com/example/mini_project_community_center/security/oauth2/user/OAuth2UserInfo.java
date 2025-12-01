package com.example.mini_project_community_center.security.oauth2.user;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected final Map<String, Object> attributes;

    protected OAuth2UserInfo(Map<String, Object> attributes) { this.attributes = attributes;}

    public Map<String, Object> getAttributes() { return attributes;}

    // provider별 고유ID (sub, id 등)
    public abstract String getId();

    public abstract String getEmail();

    public abstract String getName();
}
