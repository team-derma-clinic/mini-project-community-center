package com.example.mini_project_community_center.common.apis;

public class SessionApi {
    private SessionApi() {}

    public static final String ROOT = "/sessions";
    public static final String BY_SESSION_ID = ROOT + "/{sessionId}";
    public static final String ONLY_BY_ID = "/{sessionId}";
    public static final String STATUS = BY_SESSION_ID + "/status";
}
