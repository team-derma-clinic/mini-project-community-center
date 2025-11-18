package com.example.mini_project_community_center.common.apis;

import com.example.mini_project_community_center.common.constants.ApiMappingPattern;

public class ReviewApi {
    private ReviewApi() {}
    public static final String ROOT = ApiBase.BASE + "/reviews";
    public static final String CREATE = ROOT;
    public static final String BY_ID = ROOT + "/{reviewId}";
    public static final String DELETE = BY_ID;
    public static final String ME = ROOT + "/me";
    public static final String BY_COURSE = ApiMappingPattern.Courses.BY_COURSE_ID + "/reviews";
}
