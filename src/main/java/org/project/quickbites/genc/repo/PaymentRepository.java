package org.project.quickbites.genc.repo;

import java.util.Optional;

import org.project.quickbites.genc.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findTopByOrderIdOrderByCreatedTimestampDesc(String orderId);
}


