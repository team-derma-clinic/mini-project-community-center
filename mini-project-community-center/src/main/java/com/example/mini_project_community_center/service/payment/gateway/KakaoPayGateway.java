package com.example.mini_project_community_center.service.payment.gateway;

import com.example.mini_project_community_center.dto.payment.request.PaymentRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoPayGateway implements PaymentGateway {

    @Override
    public PaymentResult pay(PaymentRequestDto request) {
        return null;
    }
}
