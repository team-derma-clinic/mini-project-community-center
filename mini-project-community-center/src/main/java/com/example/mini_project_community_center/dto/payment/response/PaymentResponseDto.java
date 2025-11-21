package com.example.mini_project_community_center.dto.payment.response;

import com.example.mini_project_community_center.common.enums.PaymentStatus;
import com.example.mini_project_community_center.entity.Payment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PaymentResponseDto(
        Long paymentId,
        Long enrollmentId,
        BigDecimal amount,
        String currency,
        String method,
        PaymentStatus status,
        LocalDateTime paidAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PaymentResponseDto from(Payment payment) {
        return new PaymentResponseDto(
                payment.getId(),
                payment.getEnrollment().getId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getPaidAt(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}
