package com.example.mini_project_community_center.common.apis;

public class ReviewFileApi {
    private ReviewFileApi() {}

    public static final String ROOT = ApiBase.BASE + "/reviews";
    public static final String FILES_BY_REVIEW = "/{reviewId}/files";

    public static final String UPLOAD = FILES_BY_REVIEW;
    public static final String LIST =  FILES_BY_REVIEW;
    public static final String UPDATE = FILES_BY_REVIEW;

    public static final String FILE_BY_ID = "/files/{fileId}";
    public static final String DELETE =  FILE_BY_ID;
}
