package com.example.mini_project_community_center.service.payment;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.payment.request.PaymentRequestDto;
import com.example.mini_project_community_center.dto.payment.response.PaymentResponseDto;
import com.example.mini_project_community_center.security.UserPrincipal;
import jakarta.validation.Valid;

public interface PaymentService {
    ResponseDto<PaymentResponseDto> createPayment(UserPrincipal userPrincipal, @Valid PaymentRequestDto req);

    ResponseDto<PaymentResponseDto> getPaymentById(UserPrincipal userPrincipal, Long paymentId);

    ResponseDto<PaymentResponseDto> refundPayment(UserPrincipal userPrincipal, Long paymentId);
}
