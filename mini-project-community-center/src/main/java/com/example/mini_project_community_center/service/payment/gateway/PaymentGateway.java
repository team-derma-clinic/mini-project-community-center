package com.example.mini_project_community_center.service.payment.gateway;

import com.example.mini_project_community_center.dto.payment.request.PaymentRequestDto;

public interface PaymentGateway {
    PaymentResult pay(PaymentRequestDto request);
}
