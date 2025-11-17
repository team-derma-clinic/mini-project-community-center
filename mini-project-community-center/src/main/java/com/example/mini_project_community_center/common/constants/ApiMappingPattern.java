package com.example.mini_project_community_center.common.constants;

public class ApiMappingPattern {
    public static final String API = "/api";
    public static final String V1 = "/v1";
    public static final String BASE = API + V1;

    public static final class Auth {
        private Auth() {}
        public static final String ROOT = BASE + "/auth";
        public static final String LOGIN = ROOT + "/login";
        public static final String LOGOUT = ROOT + "/logout";
        public static final String REFRESH = ROOT + "/refresh";
    }

    public static final class Users {
        private Users () {}
        public static final String ROOT = BASE + "/users";
        public static final String ME = ROOT + "/me";
        public static final String BY_ID = ROOT + "/{userId}";
    }

    public static final class Roles {
        private Roles() {}
        public static final String ROOT = BASE + "/users/{userId}/roles";
        public static final String BY_NAME = ROOT + "{roleName}";
    }

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
        public static final String ROOT = BASE + "/payments";
        public static final String BY_ID = "/{paymentId}";
        public static final String PAYMENT_BY_ID = ROOT + BY_ID;
        public static final String PAYMENT_REFUND = PAYMENT_BY_ID + "/refund";
    }

    public static final class Centers {
        private Centers() {}
        public static final String ROOT = BASE + "/centers";
        public static final String BY_CENTER_ID = ROOT + "/{centerId}";
    }

    public static final class Courses {
        private Courses() {}
        public static final String ROOT = BASE + "/courses";
        public static final String BY_COURSE_ID = ROOT + "/{courseId}";
        public static final String BY_STATUS = BY_COURSE_ID + "/status";
    }

    public static final class Sessions {
        private Sessions() {}
        public static final String ROOT = BASE + "/sessions";
        public static final String BY_SESSION_ID = ROOT + "/{sessionId}";
        public static final String BY_COURSE_ID = Courses.BY_COURSE_ID + "/sessions";
        public static final String BY_STATUS = BY_SESSION_ID + "/status";
    }

    public static final class Attendance {
        private Attendance() {}
        public static final String ROOT = BASE + "/attendance";
        public static final String BY_ID = ROOT + "/{attendanceId}";
        public static final String BY_COURSE = Courses.BY_COURSE_ID + "/attendance";
        public static final String BY_SESSION = Sessions.BY_SESSION_ID + "/attendance";
    }

    public static final class Reviews {
        private Reviews() {}
        public static final String ROOT = BASE + "/reviews";
        public static final String BY_ID = ROOT + "/{reviewId}";
        public static final String ME = ROOT + "/me";
        public static final String BY_COURSE = Courses.BY_COURSE_ID + "/reviews";
    }

    public static final class Dashboard {
        private Dashboard() {}
        public static final String INSTRUCTOR_ME = BASE + "/instructors/me/dashboard";
        public static final String STAFF = BASE + "/staff/dashboard";
    }

    public static final class Reports {
        private Reports() {}
        public static final String ROOT = BASE + "/reports";
        public static final String COURSES = ROOT + "/courses";
        public static final String ATTENDANCE = ROOT + "/attendance";
        public static final String CATEGORIES = ROOT + "/categories";
        public static final String INSTRUCTORS = ROOT + "/instructors";
    }

    public static final class SystemStatus {
        private SystemStatus() {}
        public static final String HEALTH = "/health";
        public static final String INFO = "/info";
    }
}
