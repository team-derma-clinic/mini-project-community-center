package com.example.mini_project_community_center.common.apis;

public class EnrollmentApi {
    private EnrollmentApi() {}
    public static final String ROOT = ApiBase.BASE + "/enrollments";
    public static final String ME = ROOT + "/me";
    public static final String BY_ID = "/{enrollmentId}";;
    public static final String ENROLLMENT_CANCEL = BY_ID + "/cancel";
    public static final String ENROLLMENT_REFUND = BY_ID + "/refund";
}
