package com.example.mini_project_community_center.service.map.impl;

import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.config.NaverMapProperties;
import com.example.mini_project_community_center.dto.map.NaverReverseGeoResponse;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.service.map.NaverMapService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NaverMapServiceImpl implements NaverMapService {
    private final NaverMapProperties properties;
    private final ObjectMapper objectMapper;

    // 좌표 -> 주소 변환(역지오 코딩)
    @Override
    public NaverReverseGeoResponse reverseGeoCode(double latitude, double longitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("유효하지 않은 위도값입니다: " + latitude);
        }

        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("유효하지 않은 경도값입니다: " + longitude);
        }

        String url = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc"
                + "?coords=" + longitude + "," + latitude
                + "&orders=roadaddr,addr"
                + "&output=json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", properties.getClientId());
        headers.set("X-NCP-APIGW-API-KEY", properties.getClientSecret());

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

            return objectMapper.readValue(response.getBody(), NaverReverseGeoResponse.class);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.NAVER_API_ERROR);
        }
    }
}
