# Simplified DTOs - Much Easier!

You were absolutely right! We had too many DTOs. Now it's much simpler:

## Before (4 DTOs):
- PaymentRequest.java
- PaymentResponse.java  
- PaymentMethod.java (enum)
- PaymentStatus.java (enum)

## After (1 DTO):
- Payment.java - handles everything!

## Simple Payment DTO:
```java
@Data
public class Payment {
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
```

## Usage Examples:

### Card Payment:
```json
{
  "orderId": "ORDER123",
  "paymentMethod": "CARD",
  "amount": 100.00,
  "cardNumber": "4532015112830366"
}
```

### UPI Payment:
```json
{
  "orderId": "ORDER123",
  "paymentMethod": "UPI", 
  "amount": 100.00,
  "upiId": "user@paytm"
}
```

### COD Payment:
```json
{
  "orderId": "ORDER123",
  "paymentMethod": "COD",
  "amount": 100.00
}
```

Much simpler! Just one DTO for everything! 🎉
