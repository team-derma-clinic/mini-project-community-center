package com.example.mini_project_community_center.service.payment.gateway;

import lombok.Builder;

@Builder
public record PaymentResult(
        boolean success,        // 결제 성공 여부
        String paymentKey,      // PG가 발급한 결제 키 (또는 내부 생성 키)
        String failureCode,     // 실패 코드 (PG 에서 온 코드 OR 내부 코드)
        String failureMessage   // 실패 상세 메시지
) {}

