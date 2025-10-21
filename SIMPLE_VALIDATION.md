# Simple Payment Validation

Very easy card and UPI validation with minimal code.

## What's Added

### 1. SimpleValidation.java (Only 15 lines!)
```java
public class SimpleValidation {
    // Check if card is 16 digits
    public static boolean isValidCard(String cardNumber) {
        if (cardNumber == null) return false;
        String clean = cardNumber.replaceAll("\\D", "");
        return clean.length() == 16 && clean.matches("\\d+");
    }
    
    // Check if UPI has @ symbol
    public static boolean isValidUpi(String upiId) {
        if (upiId == null) return false;
        return upiId.contains("@") && upiId.length() > 5;
    }
}
```

### 2. Updated PaymentRequest.java
- Added `cardNumber` field for CARD payments
- Added `upiId` field for UPI payments
- Removed complex validation annotations

### 3. Updated PaymentServiceImpl.java
- Added simple validation in `createPayment()` method
- Only 4 lines of validation code!

## How It Works

### Card Payment
```json
{
  "orderId": "ORDER123",
  "paymentMethod": "CARD",
  "amount": 100.00,
  "cardNumber": "1234567890123456"
}
```

### UPI Payment
```json
{
  "orderId": "ORDER123", 
  "paymentMethod": "UPI",
  "amount": 100.00,
  "upiId": "user@paytm"
}
```

### COD Payment
```json
{
  "orderId": "ORDER123",
  "paymentMethod": "COD", 
  "amount": 100.00
}
```

## Validation Rules

- **Card**: Must be exactly 16 digits
- **UPI**: Must contain @ symbol and be longer than 5 characters
- **COD**: No validation needed

That's it! Very simple and easy to understand.
