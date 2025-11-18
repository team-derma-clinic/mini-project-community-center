package com.example.mini_project_community_center.common.apis;

public class RoleApi {
    private RoleApi() {}
    public static final String ROOT = ApiBase.BASE + "/users/{userId}/roles";
    public static final String BY_NAME = ROOT + "{roleName}";
}
