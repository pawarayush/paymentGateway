package org.project.quickbites.genc.util;

public class SimpleValidation {
    
    // Card validation with simple Luhn check
    public static boolean isValidCard(String cardNumber) {
        if (cardNumber == null) return false;
        String clean = cardNumber.replaceAll("\\D", "");
        if (clean.length() != 16) return false;
        
        // Simple Luhn algorithm
        int sum = 0;
        boolean doubleDigit = false;
        for (int i = clean.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(clean.charAt(i));
            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) digit = digit % 10 + 1;
            }
            sum += digit;
            doubleDigit = !doubleDigit;
        }
        return sum % 10 == 0;
    }
    
    // Simple UPI validation - just check if it has @ symbol
    public static boolean isValidUpi(String upiId) {
        if (upiId == null) return false;
        return upiId.contains("@") && upiId.length() > 5;
    }
}
