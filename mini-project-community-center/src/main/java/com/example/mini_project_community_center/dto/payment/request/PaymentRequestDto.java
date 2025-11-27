package com.example.mini_project_community_center.dto.payment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PaymentRequestDto(
        @NotNull(message = "enrollmentId 는 비워질 수 없습니다.")
        Long enrollmentId,

        @NotNull(message = "amount 는 비워질 수 없습니다.")
        @Min(value = 10000, message = "최소 결제 금액은 10000원 입니다.")
        BigDecimal amount,

        @NotBlank(message = "method 는 비워질 수 없습니다.")
        String method,

        @NotBlank(message = "currency 는 비워질 수 없습니다.")
        @Size(min = 3, max = 3, message = "currency 는 3글자여야 합니다")
        String currency,

        @NotBlank(message = "주문 ID는 필수입니다")
        String orderId,

        @NotBlank(message = "결제 키값은 필수입니다")
        String paymentKey
) {}
