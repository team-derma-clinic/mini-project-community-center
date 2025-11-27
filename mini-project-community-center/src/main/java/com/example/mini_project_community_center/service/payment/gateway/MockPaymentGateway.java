package com.example.mini_project_community_center.service.payment.gateway;

import com.example.mini_project_community_center.dto.payment.request.PaymentRequestDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MockPaymentGateway implements PaymentGateway {

    @Override
    public PaymentResult pay(PaymentRequestDto request) {
        // 항상 성공한다고 가정하는 모의결제
        String paymentKey = "MOCK-" + UUID.randomUUID();

        return PaymentResult.builder()
                .success(true)
                .paymentKey(paymentKey)
                .build();
    }
}
