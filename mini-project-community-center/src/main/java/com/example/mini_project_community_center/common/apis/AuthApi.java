package com.example.mini_project_community_center.common.apis;

public class AuthApi {
    private AuthApi() {}
    public static final String ROOT = ApiBase.BASE + "/auth";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String REFRESH = "/refresh";

    public static final String PASSWORD_RESET = "/password/reset";
    public static final String PASSWORD_VERIFY = "/password/verify";
    public static final String PASSWORD_EMAIL = "/password/email";
}
