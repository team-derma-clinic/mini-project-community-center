package com.example.mini_project_community_center.service.payment.gateway;

import com.example.mini_project_community_center.common.enums.payment.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentGatewayResolver {
    private final MockPaymentGateway mockPaymentGateway;
    private final TossPayGateway tossPayGateway;
    private final KakaoPayGateway kakaoPayGateway;

    // 결제 수단에 따라 대응되는 Gateway 구현체 반환
    public PaymentGateway resolve(PaymentMethod method) {
        return switch (method) {
            case MOCK -> mockPaymentGateway;
            case TOSS_PAY -> tossPayGateway;
            case KAKAO_PAY -> kakaoPayGateway;
        };
    }
}
