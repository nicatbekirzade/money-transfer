package com.example.cardms.util;

import java.util.Random;

public class CreditCardGenerator {

    private static final Random random = new Random();

    public static String generateCardNumber(String iin) {
        if (iin == null || iin.length() != 6 || !iin.matches("\\d{6}")) {
            throw new IllegalArgumentException("IIN must be a 6-digit number.");
        }

        StringBuilder cardNumber = new StringBuilder(iin);

        // Generate 9-digit random account number
        for (int i = 0; i < 9; i++) {
            cardNumber.append(random.nextInt(10));
        }

        // Calculate Luhn checksum digit
        int checkDigit = calculateLuhnCheckDigit(cardNumber.toString());
        cardNumber.append(checkDigit);

        return cardNumber.toString(); // total 16 digits
    }

    public static boolean isValidCardNumber(String cardNumber) {
        if (cardNumber == null || !cardNumber.matches("\\d{16}")) {
            return false;
        }

        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if (alternate) {
                digit *= 2;
                if (digit > 9) digit -= 9;
            }
            sum += digit;
            alternate = !alternate;
        }

        return sum % 10 == 0;
    }

    private static int calculateLuhnCheckDigit(String numberWithoutChecksum) {
        int sum = 0;
        boolean alternate = true;

        for (int i = numberWithoutChecksum.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(numberWithoutChecksum.charAt(i));
            if (alternate) {
                digit *= 2;
                if (digit > 9) digit -= 9;
            }
            sum += digit;
            alternate = !alternate;
        }
        return (10 - (sum % 10)) % 10;
    }
}