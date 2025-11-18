package com.example.mini_project_community_center.common.apis;

public class EnrollmentApi {
    private EnrollmentApi() {}
    public static final String ROOT = ApiBase.BASE + "/enrollments";
    public static final String ME = ROOT + "/me";
    public static final String BY_ID = "/{enrollmentId}";
    public static final String ENROLLMENT_BY_ID = ROOT + BY_ID;
    public static final String ENROLLMENT_CANCEL = ENROLLMENT_BY_ID + "/cancel";
    public static final String ENROLLMENT_REFUND = ENROLLMENT_BY_ID + "/refund";
}
