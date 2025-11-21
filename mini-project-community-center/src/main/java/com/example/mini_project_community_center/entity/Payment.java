package com.example.mini_project_community_center.entity;

import com.example.mini_project_community_center.common.enums.PaymentStatus;
import com.example.mini_project_community_center.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

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
                @Index(name = "idx_pay_paid_at", columnList = "paid_at"),
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

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, columnDefinition = "CHAR(3)")
    private String currency = "KRW";

    @Column(name = "method", nullable = false, length = 20)
    private String method;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    public static Payment createPayment(
            Enrollment enrollment,
            BigDecimal amount,
            String currency,
            String method,
            PaymentStatus status,
            LocalDateTime paidAt
    ) {
        Payment payment = new Payment();
        payment.enrollment = enrollment;
        payment.amount = amount;
        payment.currency = currency;
        payment.method = method;
        payment.status = status;
        payment.paidAt = paidAt != null ? paidAt : LocalDateTime.now();
        return payment;
    }

    public void failed() {
        this.status = PaymentStatus.FAILED;
    }

    public void markPaid() {
        this.status = PaymentStatus.PAID;
    }

    public void refund() {
        this.status = PaymentStatus.REFUNDED;
    }
}
