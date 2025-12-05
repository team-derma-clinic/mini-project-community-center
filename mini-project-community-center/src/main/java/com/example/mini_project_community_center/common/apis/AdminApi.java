package com.example.mini_project_community_center.common.apis;

public class RoleApi {
    private RoleApi() {}
    public static final String ROOT = ApiBase.BASE + "/admin/users/{userId}";
    public static final String ROLE = "/roles";
    public static final String APPROVE = "/approve";
    public static final String REJECT = "/reject";
    public static final String BY_NAME = "/{roleName}";
}
