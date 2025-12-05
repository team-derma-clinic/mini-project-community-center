package com.example.mini_project_community_center.service.map;

import com.example.mini_project_community_center.dto.map.NaverReverseGeoResponse;

public interface NaverMapService {
    NaverReverseGeoResponse reverseGeoCode(double latitude, double longitude);
}
