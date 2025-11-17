package com.example.mini_project_community_center.common.constants;

public class ApiMappingPattern {
    public static final String API = "/api";
    public static final String V1 = "/v1";
    public static final String BASE = API + V1;

    public static final class Enrollments {
        private Enrollments() {}
        public static final String ROOT = BASE + "/enrollments";
        public static final String ME = ROOT + "/me";
        public static final String BY_ID = "/{enrollmentId}";
        public static final String ENROLLMENT_BY_ID = ROOT + BY_ID;
        public static final String ENROLLMENT_CANCEL = ENROLLMENT_BY_ID + "/cancel";
        public static final String ENROLLMENT_REFUND = ENROLLMENT_BY_ID + "/refund";
    }

    public static final class Payments {
        private Payments() {}
        public static final String ROOT = BASE + "/Payments";
        public static final String BY_ID = "/{paymentId}";
        public static final String PAYMENT_BY_ID = ROOT + BY_ID;
        public static final String PAYMENT_REFUND = PAYMENT_BY_ID + "/refund";
    }
}
