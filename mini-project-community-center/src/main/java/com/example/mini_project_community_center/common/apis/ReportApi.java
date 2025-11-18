package com.example.mini_project_community_center.common.apis;

public class ReportApi {
    private ReportApi() {}
    public static final String ROOT = ApiBase.BASE + "/reports";
    public static final String COURSES = ROOT + "/courses";
    public static final String ATTENDANCE = ROOT + "/attendance";
    public static final String CATEGORIES = ROOT + "/categories";
    public static final String INSTRUCTORS = ROOT + "/instructors";
}
