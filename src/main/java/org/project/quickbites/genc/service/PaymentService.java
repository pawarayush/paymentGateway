package org.project.quickbites.genc.service;

import org.project.quickbites.genc.dto.Payment;

public interface PaymentService {
    Payment createPayment(Payment request);
    Payment getPaymentById(Long paymentId);
    Payment getPaymentByOrderId(String orderId);
    Payment updateStatus(Long paymentId, String status);
    Payment markDelivered(Long paymentId);
}


