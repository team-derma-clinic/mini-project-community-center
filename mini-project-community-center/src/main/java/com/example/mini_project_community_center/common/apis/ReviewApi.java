package com.example.mini_project_community_center.common.apis;

public class ReviewApi {
    private ReviewApi() {}
    public static final String ROOT = ApiBase.BASE + "/reviews";

    public static final String ID_ONLY = ROOT + "/{reviewId}";
    public static final String BY_ID = ID_ONLY;

    public static final String MY_REVIEWS = ROOT + "/me";
    public static final String COURSE_REVIEWS = ApiBase.BASE + CourseApi.BY_COURSE_ID + "/reviews";
}
