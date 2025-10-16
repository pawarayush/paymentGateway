package org.project.quickbites.genc.service;

import org.project.quickbites.genc.dto.PaymentRequest;
import org.project.quickbites.genc.dto.PaymentResponse;
import org.project.quickbites.genc.dto.PaymentStatus;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest request);
    PaymentResponse getPaymentById(Long paymentId);
    PaymentResponse getPaymentByOrderId(String orderId);
    PaymentResponse updateStatus(Long paymentId, PaymentStatus status);
    PaymentResponse markDelivered(Long paymentId);
}


