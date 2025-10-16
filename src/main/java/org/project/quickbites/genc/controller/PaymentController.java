package org.project.quickbites.genc.controller;

import org.project.quickbites.genc.dto.PaymentRequest;
import org.project.quickbites.genc.dto.PaymentResponse;
import org.project.quickbites.genc.dto.PaymentStatus;
import org.project.quickbites.genc.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Validated @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.createPayment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getById(@PathVariable Long paymentId) {
        PaymentResponse response = paymentService.getPaymentById(paymentId);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PaymentResponse> getByOrderId(@RequestParam("orderId") String orderId) {
        PaymentResponse response = paymentService.getPaymentByOrderId(orderId);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<PaymentResponse> updateStatus(@PathVariable Long paymentId,
                                                        @RequestParam("status") PaymentStatus status) {
        PaymentResponse response = paymentService.updateStatus(paymentId, status);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{paymentId}/delivered")
    public ResponseEntity<PaymentResponse> markDelivered(@PathVariable Long paymentId) {
        PaymentResponse response = paymentService.markDelivered(paymentId);
        if (response == null) {
            // either not found or invalid transition for non-COD or non-pending
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(response);
    }
}


