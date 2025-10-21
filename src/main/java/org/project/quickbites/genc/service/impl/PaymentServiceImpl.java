package org.project.quickbites.genc.service.impl;

import org.project.quickbites.genc.dto.Payment;
import org.project.quickbites.genc.entity.PaymentEntity;
import org.project.quickbites.genc.repo.PaymentRepository;
import org.project.quickbites.genc.service.PaymentService;
import org.project.quickbites.genc.util.SimpleValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public Payment createPayment(Payment request) {
        // Simple validation
        if ("CARD".equals(request.getPaymentMethod()) && !SimpleValidation.isValidCard(request.getCardNumber())) {
            throw new IllegalArgumentException("Invalid card number");
        }
        if ("UPI".equals(request.getPaymentMethod()) && !SimpleValidation.isValidUpi(request.getUpiId())) {
            throw new IllegalArgumentException("Invalid UPI ID");
        }
        
        PaymentEntity entity = new PaymentEntity();
        entity.setOrderId(request.getOrderId());
        entity.setPaymentMethod(request.getPaymentMethod());
        entity.setAmount(request.getAmount());
        
        // COD -> PENDING until delivered; others -> SUCCESS on pay
        if ("COD".equals(request.getPaymentMethod())) {
            entity.setStatus("PENDING");
        } else {
            entity.setStatus("SUCCESS");
        }
        PaymentEntity saved = paymentRepository.save(entity);
        return toDto(saved);
    }

    @Override
    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findTopByOrderIdOrderByCreatedTimestampDesc(orderId)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public Payment updateStatus(Long paymentId, String status) {
        return paymentRepository.findById(paymentId)
                .map(p -> {
                    p.setStatus(status);
                    return toDto(paymentRepository.save(p));
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public Payment markDelivered(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .map(p -> {
                    // Only allow COD and only from PENDING -> SUCCESS
                    boolean isCod = "COD".equalsIgnoreCase(p.getPaymentMethod());
                    boolean isPending = "PENDING".equalsIgnoreCase(p.getStatus());
                    if (isCod && isPending) {
                        p.setStatus("SUCCESS");
                        return toDto(paymentRepository.save(p));
                    }
                    return null;
                })
                .orElse(null);
    }

    private Payment toDto(PaymentEntity entity) {
        if (entity == null) return null;
        Payment dto = new Payment();
        dto.setPaymentId(entity.getPaymentId());
        dto.setOrderId(entity.getOrderId());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setAmount(entity.getAmount());
        dto.setStatus(entity.getStatus());
        dto.setCreatedTimestamp(entity.getCreatedTimestamp());
        dto.setUpdatedTimestamp(entity.getUpdatedTimestamp());
        return dto;
    }
}


