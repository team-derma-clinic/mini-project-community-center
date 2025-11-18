package com.example.mini_project_community_center.common.apis;

public class CenterApi {
        private CenterApi() {}

        public static final String ROOT = ApiBase.BASE + "/centers";
        public static final String BY_CENTER_ID = "/{centerId}";
}
