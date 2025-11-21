package com.example.mini_project_community_center.common.apis;

public class AttendanceApi {
    private AttendanceApi() {}
    public static final String ROOT = ApiBase.BASE + "/attendance";
    public static final String UPSERT = ROOT;
    public static final String BY_ID = ROOT + "/{attendanceId}";
    public static final String DETAIL = BY_ID;
    public static final String UPDATE = BY_ID;
    public static final String BY_COURSE = ApiBase.BASE + CourseApi.BY_COURSE_ID + "/attendance";
    public static final String BY_SESSION = ApiBase.BASE + SessionApi.BY_SESSION_ID + "/attendance";
}
