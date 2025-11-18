package com.example.mini_project_community_center.common.apis;

public class UserApi {
    private UserApi () {}
    public static final String ROOT = ApiBase.BASE + "/users";
    public static final String ME = ROOT + "/me";
    public static final String BY_ID = ROOT + "/{userId}";
}
