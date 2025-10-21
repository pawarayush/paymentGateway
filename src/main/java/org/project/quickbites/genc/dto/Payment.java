package org.project.quickbites.genc.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class Payment {
    // For requests and responses
    private Long paymentId;
    private String orderId;
    private String paymentMethod; // "CARD", "UPI", "COD"
    private BigDecimal amount;
    private String status; // "SUCCESS", "FAILED", "PENDING"
    private String cardNumber; // For CARD payments
    private String upiId; // For UPI payments
    private OffsetDateTime createdTimestamp;
    private OffsetDateTime updatedTimestamp;
}
