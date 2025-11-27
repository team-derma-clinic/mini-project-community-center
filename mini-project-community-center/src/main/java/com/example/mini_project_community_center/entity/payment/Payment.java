package com.example.mini_project_community_center.entity.payment;

import com.example.mini_project_community_center.common.enums.payment.PaymentStatus;
import com.example.mini_project_community_center.entity.base.BaseTimeEntity;
import com.example.mini_project_community_center.entity.enrollment.Enrollment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "payments",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_pay_enroll", columnNames = "enrollment_id"),
        },
        indexes = {
                @Index(name = "idx_pay_enroll", columnList = "enrollment_id, status"),
                @Index(name = "idx_pay_requestedAt", columnList = "requestedAt"),
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", foreignKey = @ForeignKey(name = "fk_payment_enrollment"), nullable = false)
    private Enrollment enrollment;

    @Column(nullable = false, length = 100)
    private String orderId;

    @Column(nullable = false, length = 100, unique = true)
    private String paymentKey;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, columnDefinition = "CHAR(3)")
    private String currency = "KRW";

    @Column(name = "method", nullable = false, length = 20)
    private String method;

    @Column(length = 50)
    private String failureCode;

    @Column(length = 255)
    private String failureMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PaymentStatus status = PaymentStatus.READY;

    @CreationTimestamp
    @Column(name = "requested_at", nullable = false, updatable = false)
    private LocalDateTime requestedAt;

    private LocalDateTime approvedAt;
    private LocalDateTime cancelledAt;

    public static Payment createPayment(
            Enrollment enrollment,
            BigDecimal amount,
            String currency,
            String method,
            PaymentStatus status
    ) {
        Payment payment = new Payment();
        payment.enrollment = enrollment;
        payment.amount = amount;
        payment.currency = currency;
        payment.method = method;
        payment.status = status;
        return payment;
    }
    public void markSuccess() {
        this.status = PaymentStatus.SUCCESS;
        this.approvedAt = LocalDateTime.now();
        this.failureCode = null;
        this.failureMessage = null;
    }

    public void markFailed(String code, String message) {
        this.status = PaymentStatus.FAILED;
        this.failureCode = code;
        this.failureMessage = message;
    }

    public void markRefunded() {
        this.status = PaymentStatus.REFUNDED;
        this.cancelledAt = LocalDateTime.now();
    }
}
