package com.example.mini_project_community_center.service.payment.impl;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.payment.request.PaymentRequestDto;
import com.example.mini_project_community_center.dto.payment.response.PaymentResponseDto;
import com.example.mini_project_community_center.security.UserPrincipal;
import com.example.mini_project_community_center.service.payment.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public ResponseDto<PaymentResponseDto> createPayment(UserPrincipal userPrincipal, PaymentRequestDto req) {
        return null;
    }

    @Override
    public ResponseDto<PaymentResponseDto> getPaymentById(UserPrincipal userPrincipal, Long paymentId) {
        return null;
    }

    @Override
    public ResponseDto<PaymentResponseDto> refundPayment(UserPrincipal userPrincipal, Long paymentId) {
        return null;
    }
}
