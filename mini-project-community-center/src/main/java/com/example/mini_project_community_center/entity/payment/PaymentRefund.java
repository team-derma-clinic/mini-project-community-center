package com.example.mini_project_community_center.entity.payment;

import com.example.mini_project_community_center.common.enums.payment.RefundStatus;
import com.example.mini_project_community_center.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_refunds")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PaymentRefund extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(nullable = false)
    private Long amount;

    // 환불 사유 위치 확인필요
//    @Column(length = 255)
//    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RefundStatus status;

    @Column(length = 50)
    private String failureCode;

    @Column(length = 255)
    private String failureMessage;

    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;


    public void markCompleted() {
        this.status = RefundStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        this.failureCode = null;
        this.failureMessage = null;
    }

    public void markFailed(String code, String message) {
        this.status = RefundStatus.FAILED;
        this.failureCode = code;
        this.failureMessage = message;
    }
}