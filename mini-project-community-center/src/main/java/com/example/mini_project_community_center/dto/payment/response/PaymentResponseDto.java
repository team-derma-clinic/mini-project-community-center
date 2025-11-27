package com.example.mini_project_community_center.dto.payment.response;

import com.example.mini_project_community_center.common.enums.payment.PaymentStatus;
import com.example.mini_project_community_center.entity.payment.Payment;
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
        LocalDateTime approvedAt,
        LocalDateTime requestedAt,
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
                payment.getApprovedAt(),
                payment.getRequestedAt(),
                payment.getUpdatedAt()
        );
    }
}
