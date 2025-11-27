package com.example.mini_project_community_center.service.center;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.center.request.CenterCreateRequest;
import com.example.mini_project_community_center.dto.center.request.CenterUpdateRequest;
import com.example.mini_project_community_center.dto.center.response.CenterDetailResponse;
import com.example.mini_project_community_center.dto.center.response.CenterListItemResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface CenterService {
    ResponseDto<CenterDetailResponse> createCenter(@Valid CenterCreateRequest req);

    ResponseDto<CenterDetailResponse> getCenterDetail(Long centerId);

    ResponseDto<Page<CenterListItemResponse>> getCenters(String q, int page, int size, String sort);

    ResponseDto<CenterDetailResponse> updateCenter(Long centerId, @Valid CenterUpdateRequest req);

    ResponseDto<Void> deleteCenter(Long centerId, boolean hardDelete);
}
