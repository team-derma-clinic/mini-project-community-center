package com.example.mini_project_community_center.service.center.impl;

import com.example.mini_project_community_center.common.utils.DateUtils;
import com.example.mini_project_community_center.common.utils.ValueMapper;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.center.request.CenterCreateRequest;
import com.example.mini_project_community_center.dto.center.request.CenterUpdateRequest;
import com.example.mini_project_community_center.dto.center.response.CenterDetailResponse;
import com.example.mini_project_community_center.dto.center.response.CenterListItemResponse;
import com.example.mini_project_community_center.entity.center.Center;
import com.example.mini_project_community_center.repository.center.CenterRepository;
import com.example.mini_project_community_center.service.center.CenterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CenterServiceImpl implements CenterService {
    /** Authorization Checker 추가 (-) */

    private final CenterRepository centerRepository;

    @Transactional
    @Override
    public ResponseDto<CenterDetailResponse> createCenter(CenterCreateRequest req) {
        Center center = Center.create(
                req.name(),
                req.address(),
                ValueMapper.toBigDecimalOrNull(req.latitude()),
                ValueMapper.toBigDecimalOrNull(req.longitude()),
                req.phone()
        );

        Center saved = centerRepository.save(center);

        CenterDetailResponse data = new CenterDetailResponse(
                saved.getId(),
                saved.getName(),
                saved.getAddress(),
                ValueMapper.toDoubleOrNull(saved.getLatitude()),
                ValueMapper.toDoubleOrNull(saved.getLongitude()),
                saved.getPhone(),
                DateUtils.toKstString(saved.getCreatedAt())
        );

        return ResponseDto.success(data);
    }

    @Override
    public ResponseDto<CenterDetailResponse> getCenterDetail(Long centerId) {
        Center center = centerRepository.findById(centerId)
                .orElseThrow(() -> new EntityNotFoundException("해당 센터를 찾을 수 없습니다. centerId: " + centerId));

        CenterDetailResponse data = new CenterDetailResponse(
                centerId,
                center.getName(),
                center.getAddress(),
                ValueMapper.toDoubleOrNull(center.getLatitude()),
                ValueMapper.toDoubleOrNull(center.getLongitude()),
                center.getPhone(),
                DateUtils.toKstString(center.getCreatedAt())
        );

        return ResponseDto.success(data);
    }

    @Override
    public ResponseDto<Page<CenterListItemResponse>> getCenters(String q, int page, int size, String sort) {
        return null;
    }

    @Transactional
    @Override
    public ResponseDto<CenterDetailResponse> updateCenter(Long centerId, CenterUpdateRequest req) {
        return null;
    }

    @Transactional
    @Override
    public ResponseDto<Void> deleteCenter(Long centerId, boolean hardDelete) {
        return null;
    }


}
