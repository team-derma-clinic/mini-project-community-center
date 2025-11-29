package com.example.mini_project_community_center.common.apis;

public class CourseFileApi {
    private CourseFileApi() {}

    public static final String ROOT = ApiBase.BASE + "/courses";

    public static final String THUMBNAIL = "/{courseId}/thumbnail";
    public static final String FILES_BY_COURSE = "/{courseId}/files";

    public static final String FILE_BY_ID = "/files/{fileId}";

    public static final String UPLOAD = THUMBNAIL;
    public static final String LIST = FILES_BY_COURSE;
    public static final String UPDATE = THUMBNAIL + "/select/{fileId}";

    public static final String DELETE = FILE_BY_ID;
}
