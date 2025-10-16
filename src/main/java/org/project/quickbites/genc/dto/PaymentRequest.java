package org.project.quickbites.genc.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// removed Luhn validation

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    @NotBlank
    private String orderId;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    @Min(0)
    private BigDecimal amount;

    // Optional; for CARD payments if you still want to capture it
    private String cardNumber;

    
}


