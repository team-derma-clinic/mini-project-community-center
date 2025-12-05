package com.example.mini_project_community_center.service.center.impl;

import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.common.utils.ValueMapper;
import com.example.mini_project_community_center.dto.PageRequestDto;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.center.request.CenterCreateRequest;
import com.example.mini_project_community_center.dto.center.request.CenterUpdateRequest;
import com.example.mini_project_community_center.dto.center.response.CenterDetailResponse;
import com.example.mini_project_community_center.dto.center.response.CenterListItemResponse;
import com.example.mini_project_community_center.dto.map.NaverReverseGeoResponse;
import com.example.mini_project_community_center.entity.center.Center;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.repository.center.CenterRepository;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.center.CenterService;
import com.example.mini_project_community_center.service.map.NaverMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CenterServiceImpl implements CenterService {
    private final CenterRepository centerRepository;
    private final NaverMapService naverMapService;

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public ResponseDto<CenterDetailResponse> createCenter(UserPrincipal userPrincipal, CenterCreateRequest req) {

        BigDecimal latitude = ValueMapper.toBigDecimalOrNull(req.latitude());
        BigDecimal longitude = ValueMapper.toBigDecimalOrNull(req.longitude());

        if ((latitude != null && longitude == null) || (latitude == null & longitude != null)) {
            throw new IllegalArgumentException("경도, 위도 두가지 값 모두 필요합니다.");
        }

        String finalAddress = null;

        if ((req.address() == null || req.address().isBlank()) && latitude != null && longitude != null) {
            NaverReverseGeoResponse dto = naverMapService.reverseGeoCode(latitude.doubleValue(), longitude.doubleValue());
            finalAddress = dto.extractFullAddress();
        }

        if (finalAddress != null) {
            validateAddressCoordinateMatch(finalAddress, latitude.doubleValue(), longitude.doubleValue());
        }

        Center center = Center.create(
                req.name(),
                finalAddress,
                latitude,
                longitude,
                req.phone()
        );

        Center saved = centerRepository.save(center);

        CenterDetailResponse data = CenterDetailResponse.fromEntity(saved);

        return ResponseDto.success(data);
    }

    @Override
    public ResponseDto<CenterDetailResponse> getCenterDetail(Long centerId) {
        Center center = centerRepository.findById(centerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 센터가 존재하지 않습니다. centerId: " + centerId));

        CenterDetailResponse data = CenterDetailResponse.fromEntity(center);
        return ResponseDto.success(data);
    }

    @Override
    public ResponseDto<Page<CenterListItemResponse>> getCenters(String q, PageRequestDto req) {
        Pageable pageable = req.toPageable();

        Page<Center> pageResult;

        if (q == null || q.isBlank()) {
            pageResult = centerRepository.findAllActive(pageable);
        } else {
            pageResult = centerRepository.searchActiveByName(q, pageable);
        }

        Page<CenterListItemResponse> data = pageResult.map(center -> new CenterListItemResponse(
                center.getId(),
                center.getName(),
                center.getAddress()
        ));
        return ResponseDto.success(data);
    }

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public ResponseDto<CenterDetailResponse> updateCenter(UserPrincipal userPrincipal, Long centerId, CenterUpdateRequest req) {
        Center center = centerRepository.findById(centerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 센터가 존재하지 않습니다. centerId: " + centerId));

        center.update(
                req.name(),
                req.address(),
                ValueMapper.toBigDecimalOrNull(req.latitude()),
                ValueMapper.toBigDecimalOrNull(req.longitude()),
                req.phone()
        );

        Center updated = centerRepository.save(center);

        CenterDetailResponse data = CenterDetailResponse.fromEntity(updated);

        return ResponseDto.success(data);
    }

    @Transactional
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<Void> deleteCenter(UserPrincipal userPrincipal, Long centerId, boolean hardDelete) {
        Center center = centerRepository.findById(centerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 센터가 존재하지 않습니다. centerId: " + centerId));

        if (hardDelete) {
            centerRepository.delete(center);
            return ResponseDto.success(null);
        }
        center.deactivate();
        return ResponseDto.success(null);
    }

    private void validateAddressCoordinateMatch(String inputAddress, double latitude, double longitude) {
        NaverReverseGeoResponse dto = naverMapService.reverseGeoCode(latitude, longitude);

        String apiArea1 = dto.results().get(0).region().area1().name();
        String apiArea2 = dto.results().get(0).region().area2().name();

        if (!inputAddress.contains(apiArea1) || !inputAddress.contains(apiArea2)) {
            throw new BusinessException(ErrorCode.ADDRESS_COORDINATE_MISMATCH);
        }
    }
}
