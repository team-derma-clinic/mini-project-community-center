package com.example.mini_project_community_center.common.apis;

public class RoleApi {
    private RoleApi() {}
    public static final String ROOT = ApiBase.BASE + "/admin/users/{userId}";
    public static final String ROLE = ROOT + "/roles";
    public static final String BY_NAME =  ROLE + "/{roleName}";
}
