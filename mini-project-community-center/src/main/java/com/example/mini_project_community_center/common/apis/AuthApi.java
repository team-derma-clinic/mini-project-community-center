package com.example.mini_project_community_center.common.apis;

public class AuthApi {
    private AuthApi() {}
    public static final String ROOT = ApiBase.BASE + "/auth";
    public static final String REGISTER = ROOT + "/register";
    public static final String LOGIN = ROOT + "/login";
    public static final String LOGOUT = ROOT + "/logout";
    public static final String REFRESH = ROOT + "/refresh";
}
