package org.project.quickbites.genc.repo;

import java.util.Optional;

import org.project.quickbites.genc.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findTopByOrderIdOrderByCreatedTimestampDesc(String orderId);
}


