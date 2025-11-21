package com.example.mini_project_community_center.common.apis;

public class AttendanceApi {
    private AttendanceApi() {}
    public static final String ROOT = ApiBase.BASE + "/attendance";

    public static final String ID_ONLY = ROOT + "/{attendanceId}";
    public static final String BY_ID = ID_ONLY;

    public static final String COURSE_LIST = ApiBase.BASE + CourseApi.BY_COURSE_ID + "/attendance";
    public static final String SESSION_LIST = ApiBase.BASE + SessionApi.BY_SESSION_ID + "/attendance";
}
