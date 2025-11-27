package com.example.mini_project_community_center.repository.payment;

import com.example.mini_project_community_center.entity.payment.PaymentRefund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRefundRepository extends JpaRepository<PaymentRefund, Long> {
}
