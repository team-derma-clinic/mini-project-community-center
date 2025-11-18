package com.example.mini_project_community_center.common.apis;

public class CourseApi {
    private CourseApi() {}

    public static final String ROOT = ApiBase.BASE + "/courses";
    public static final String BY_COURSE_ID_ONLY = "/{courseId}";
    public static final String BY_COURSE_ID = "/courses/{courseId}";
    public static final String STATUS = BY_COURSE_ID_ONLY + "/status";
}
