package com.example.mini_project_community_center.service.center.impl;

import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.common.utils.ValueMapper;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.center.request.CenterCreateRequest;
import com.example.mini_project_community_center.dto.center.request.CenterUpdateRequest;
import com.example.mini_project_community_center.dto.center.response.CenterDetailResponse;
import com.example.mini_project_community_center.dto.center.response.CenterListItemResponse;
import com.example.mini_project_community_center.entity.center.Center;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.repository.center.CenterRepository;
import com.example.mini_project_community_center.security.UserPrincipal;
import com.example.mini_project_community_center.service.center.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("")
    public ResponseDto<CenterDetailResponse> createCenter(UserPrincipal userPrincipal, CenterCreateRequest req) {

        Center center = Center.create(
                req.name(),
                req.address(),
                ValueMapper.toBigDecimalOrNull(req.latitude()),
                ValueMapper.toBigDecimalOrNull(req.longitude()),
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
    public ResponseDto<Page<CenterListItemResponse>> getCenters(String q, int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        Page<Center> pageResult;

        if(q == null || q.isBlank()) {
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
    @PreAuthorize("")
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
    @PreAuthorize("")
    public ResponseDto<Void> deleteCenter(UserPrincipal userPrincipal, Long centerId, boolean hardDelete) {
        Center center = centerRepository.findById(centerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "해당 센터가 존재하지 않습니다. centerId: " + centerId));

        if(hardDelete) {
            centerRepository.delete(center);
            return ResponseDto.success(null);
        }
        center.deactivate();
        return ResponseDto.success(null);
    }
}
