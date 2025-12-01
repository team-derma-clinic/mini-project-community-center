package com.example.mini_project_community_center.controller.payment;

import com.example.mini_project_community_center.common.apis.PaymentApi;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.payment.request.PaymentRequestDto;
import com.example.mini_project_community_center.dto.payment.response.PaymentResponseDto;
import com.example.mini_project_community_center.security.UserPrincipal;
import com.example.mini_project_community_center.service.payment.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PaymentApi.ROOT)
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    // 결제 시도 (STUDENT)
    @PostMapping
    public ResponseEntity<ResponseDto<PaymentResponseDto>> createPayment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody PaymentRequestDto req
    ) {
        ResponseDto<PaymentResponseDto> data = paymentService.createPayment(userPrincipal, req);

        return ResponseEntity.ok(data);
    }

    // 결제 상세 조회 (본인/STAFF/ADMIN)
    @GetMapping(PaymentApi.BY_ID)
    public ResponseEntity<ResponseDto<PaymentResponseDto>> getPaymentById(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long paymentId
    ) {
        ResponseDto<PaymentResponseDto> data = paymentService.getPaymentById(userPrincipal, paymentId);

        return ResponseEntity.ok(data);
    }

    // 결제 환불 (STAFF/ADMIN)
    @PutMapping(PaymentApi.PAYMENT_REFUND)
    public ResponseEntity<ResponseDto<PaymentResponseDto>> refundPayment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long paymentId
    ) {
        ResponseDto<PaymentResponseDto> data = paymentService.refundPayment(userPrincipal, paymentId);

        return ResponseEntity.ok(data);
    }
}
