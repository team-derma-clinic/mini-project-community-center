package com.example.mini_project_community_center.common.apis;

import com.example.mini_project_community_center.common.constants.ApiMappingPattern;

public class AttendanceApi {
    private AttendanceApi() {}
    public static final String ROOT = ApiBase.BASE + "/attendance";
    public static final String UPSERT = ROOT;
    public static final String BY_ID = ROOT + "/{attendanceId}";
    public static final String UPDATE = BY_ID;
    public static final String BY_COURSE = ApiMappingPattern.Courses.BY_COURSE_ID + "/attendance";
    public static final String BY_SESSION = ApiMappingPattern.Sessions.BY_SESSION_ID + "/attendance";
}
