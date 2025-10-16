package org.project.quickbites.genc.service.impl;

import org.project.quickbites.genc.dto.PaymentMethod;
import org.project.quickbites.genc.dto.PaymentRequest;
import org.project.quickbites.genc.dto.PaymentResponse;
import org.project.quickbites.genc.dto.PaymentStatus;
import org.project.quickbites.genc.entity.Payment;
import org.project.quickbites.genc.repo.PaymentRepository;
import org.project.quickbites.genc.service.PaymentService;
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
    public PaymentResponse createPayment(PaymentRequest request) {
        // If card method, ensure cardNumber present and valid per DTO validation
        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setPaymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod().name() : null);
        payment.setAmount(request.getAmount());
        // COD -> PENDING until delivered; others -> SUCCESS on pay
        if (request.getPaymentMethod() == PaymentMethod.COD) {
            payment.setStatus(PaymentStatus.PENDING.name());
        } else {
            payment.setStatus(PaymentStatus.SUCCESS.name());
        }
        Payment saved = paymentRepository.save(payment);
        return toResponse(saved);
    }

    @Override
    public PaymentResponse getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .map(this::toResponse)
                .orElse(null);
    }

    @Override
    public PaymentResponse getPaymentByOrderId(String orderId) {
        return paymentRepository.findTopByOrderIdOrderByCreatedTimestampDesc(orderId)
                .map(this::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional
    public PaymentResponse updateStatus(Long paymentId, PaymentStatus status) {
        return paymentRepository.findById(paymentId)
                .map(p -> {
                    p.setStatus(status != null ? status.name() : null);
                    return toResponse(paymentRepository.save(p));
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public PaymentResponse markDelivered(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .map(p -> {
                    // Only allow COD and only from PENDING -> SUCCESS
                    boolean isCod = "COD".equalsIgnoreCase(p.getPaymentMethod());
                    boolean isPending = PaymentStatus.PENDING.name().equalsIgnoreCase(p.getStatus());
                    if (isCod && isPending) {
                        p.setStatus(PaymentStatus.SUCCESS.name());
                        return toResponse(paymentRepository.save(p));
                    }
                    return null;
                })
                .orElse(null);
    }

    private PaymentResponse toResponse(Payment payment) {
        if (payment == null) return null;
        PaymentResponse resp = new PaymentResponse();
        resp.setPaymentId(payment.getPaymentId());
        resp.setOrderId(payment.getOrderId());
        resp.setPaymentMethod(parseMethod(payment.getPaymentMethod()));
        resp.setAmount(payment.getAmount());
        resp.setStatus(parseStatus(payment.getStatus()));
        resp.setCreatedTimestamp(payment.getCreatedTimestamp());
        resp.setUpdatedTimestamp(payment.getUpdatedTimestamp());
        return resp;
    }

    private PaymentMethod parseMethod(String method) {
        if (method == null) return null;
        try { return PaymentMethod.valueOf(method); } catch (IllegalArgumentException e) { return null; }
    }

    private PaymentStatus parseStatus(String status) {
        if (status == null) return null;
        try { return PaymentStatus.valueOf(status); } catch (IllegalArgumentException e) { return null; }
    }
}


