package com.example.mini_project_community_center.common.apis;

public class ReviewApi {
    private ReviewApi() {}
    public static final String ROOT = ApiBase.BASE + "/reviews";
    public static final String CREATE = ROOT;
    public static final String BY_ID = ROOT + "/{reviewId}";
    public static final String DELETE = BY_ID;
    public static final String ME = ROOT + "/me";
    public static final String BY_COURSE = ApiBase.BASE + CourseApi.BY_COURSE_ID + "/reviews";
}
