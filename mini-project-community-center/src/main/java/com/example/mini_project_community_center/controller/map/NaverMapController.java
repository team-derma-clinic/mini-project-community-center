package com.example.mini_project_community_center.controller.map;

import com.example.mini_project_community_center.common.apis.MapApi;
import com.example.mini_project_community_center.dto.map.NaverReverseGeoResponse;
import com.example.mini_project_community_center.service.map.NaverMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(MapApi.ROOT)
public class NaverMapController {
    private final NaverMapService mapService;

    @GetMapping(MapApi.REVERSE_GEO)
    public NaverReverseGeoResponse reverseGeoCode(@RequestParam double latitude, @RequestParam double longitude) {
        return mapService.reverseGeoCode(latitude, longitude);
    }
}
