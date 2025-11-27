package com.example.mini_project_community_center.service.payment.gateway;

import com.example.mini_project_community_center.dto.payment.request.PaymentRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TossPayGateway implements PaymentGateway {

    @Value("${payment.toss.secret-key}")
    private String secretKey;

    @Value("${payment.toss.base-url}")
    private String baseUrl;

    @Override
    public PaymentResult pay(PaymentRequestDto request) {
        try {

        } catch (Exception e) {
            log.error("[TossPayGateway] ERROR", e);
            return PaymentResult.builder()
                    .success(false)
                    .failureCode("TOSS_ERROR")
                    .failureMessage(e.getMessage())
                    .build();
        }
        return null;
    }
}
