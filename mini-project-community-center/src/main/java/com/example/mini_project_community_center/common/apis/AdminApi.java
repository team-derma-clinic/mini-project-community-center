package com.example.mini_project_community_center.common.apis;

public class AdminApi {
    private AdminApi() {}
    public static final String ROOT = ApiBase.BASE + "/admin/users";
    public static final String ROLE = "/roles";
    public static final String APPROVE = "/{userId}/approve";
    public static final String REJECT = "/{userId}/reject";
    public static final String PENDING = "/pending";
    public static final String BY_NAME = "/{roleName}";
}
