package com.example.mini_project_community_center.controller.center;

import com.example.mini_project_community_center.common.apis.CenterApi;
import com.example.mini_project_community_center.dto.PageRequestDto;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.center.request.CenterCreateRequest;
import com.example.mini_project_community_center.dto.center.request.CenterUpdateRequest;
import com.example.mini_project_community_center.dto.center.response.CenterDetailResponse;
import com.example.mini_project_community_center.dto.center.response.CenterListItemResponse;
import com.example.mini_project_community_center.security.user.UserPrincipal;
import com.example.mini_project_community_center.service.center.CenterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(CenterApi.ROOT)
@RequiredArgsConstructor
public class CenterController {
    private final CenterService centerService;

    // 센터 생성 (Staff/Admin)
    @PostMapping
    public ResponseEntity<ResponseDto<CenterDetailResponse>> createCenter(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody CenterCreateRequest req
            ) {
        ResponseDto<CenterDetailResponse> data = centerService.createCenter(userPrincipal, req);
        return ResponseEntity.ok(data);
    }

    // 센터 단건조회 (public)
    @GetMapping(CenterApi.BY_CENTER_ID)
    public ResponseEntity<ResponseDto<CenterDetailResponse>> getCenterDetail(
            @PathVariable Long centerId
    ) {
        ResponseDto<CenterDetailResponse> data = centerService.getCenterDetail(centerId);
        return ResponseEntity.ok(data);
    }

    // 센터 목록/검색 q?,page,size,sort (public)
    @GetMapping
    public ResponseEntity<ResponseDto<Page<CenterListItemResponse>>> getCenters(
            @RequestParam(required = false) String q,
            @Valid PageRequestDto req
            ) {
        ResponseDto<Page<CenterListItemResponse>> data = centerService.getCenters(q, req);
        return ResponseEntity.ok(data);
    }

    // 센터 수정(STAFF, ADMIN)
    @PutMapping(CenterApi.BY_CENTER_ID)
    public ResponseEntity<ResponseDto<CenterDetailResponse>> updateCenter(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long centerId,
            @Valid @RequestBody CenterUpdateRequest req
            ) {
        ResponseDto<CenterDetailResponse> data = centerService.updateCenter(userPrincipal, centerId, req);
        return ResponseEntity.ok(data);
    }

    // 센터 삭제(권장: 비활성 필드로 대체) (ADMIN)
    @DeleteMapping(CenterApi.BY_CENTER_ID)
    public ResponseEntity<ResponseDto<Void>> deleteCenter(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long centerId,
            @RequestParam(defaultValue = "false") boolean hardDelete) {
        ResponseDto<Void> data = centerService.deleteCenter(userPrincipal, centerId, hardDelete);
        return ResponseEntity.ok(data);
    }
}
