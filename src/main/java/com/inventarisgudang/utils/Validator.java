package com.inventarisgudang.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * Input validation utility class.
 * Provides common validation methods for user input.
 */
public class Validator {
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    // Regex patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^[+]?[0-9]{10,13}$"
    );

    private static final Pattern NUMBER_PATTERN = Pattern.compile(
            "^[0-9]+$"
    );

    private static final Pattern DECIMAL_PATTERN = Pattern.compile(
            "^[0-9]+(\\.[0-9]{1,2})?$"
    );

    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9]+$"
    );

    // Private constructor
    private Validator() {
    }

    /**
     * Validates if a string is not null and not empty.
     * 
     * @param value the value to validate
     * @return true if valid, false otherwise
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Validates if a string is not null, not empty, and has minimum length.
     * 
     * @param value the value to validate
     * @param minLength minimum required length
     * @return true if valid, false otherwise
     */
    public static boolean isMinLength(String value, int minLength) {
        return isNotEmpty(value) && value.length() >= minLength;
    }

    /**
     * Validates if a string has maximum length.
     * 
     * @param value the value to validate
     * @param maxLength maximum allowed length
     * @return true if valid, false otherwise
     */
    public static boolean isMaxLength(String value, int maxLength) {
        return isNotEmpty(value) && value.length() <= maxLength;
    }

    /**
     * Validates if a string has a specific length range.
     * 
     * @param value the value to validate
     * @param minLength minimum length
     * @param maxLength maximum length
     * @return true if valid, false otherwise
     */
    public static boolean isLengthBetween(String value, int minLength, int maxLength) {
        return isMinLength(value, minLength) && isMaxLength(value, maxLength);
    }

    /**
     * Validates email format.
     * 
     * @param email the email to validate
     * @return true if valid email format, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (!isNotEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates phone number format.
     * 
     * @param phone the phone number to validate
     * @return true if valid phone format, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        if (!isNotEmpty(phone)) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.replaceAll("[\\s()-]", "")).matches();
    }

    /**
     * Validates if a string contains only numbers.
     * 
     * @param value the value to validate
     * @return true if contains only numbers, false otherwise
     */
    public static boolean isNumeric(String value) {
        if (!isNotEmpty(value)) {
            return false;
        }
        return NUMBER_PATTERN.matcher(value).matches();
    }

    /**
     * Validates if a string is a valid decimal number.
     * 
     * @param value the value to validate
     * @return true if valid decimal, false otherwise
     */
    public static boolean isDecimal(String value) {
        if (!isNotEmpty(value)) {
            return false;
        }
        return DECIMAL_PATTERN.matcher(value).matches();
    }

    /**
     * Validates if a string contains only alphanumeric characters.
     * 
     * @param value the value to validate
     * @return true if alphanumeric only, false otherwise
     */
    public static boolean isAlphanumeric(String value) {
        if (!isNotEmpty(value)) {
            return false;
        }
        return ALPHANUMERIC_PATTERN.matcher(value).matches();
    }

    /**
     * Validates if an integer is within a range.
     * 
     * @param value the value to validate
     * @param min minimum value
     * @param max maximum value
     * @return true if within range, false otherwise
     */
    public static boolean isBetween(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * Validates if a double is within a range.
     * 
     * @param value the value to validate
     * @param min minimum value
     * @param max maximum value
     * @return true if within range, false otherwise
     */
    public static boolean isBetween(double value, double min, double max) {
        return value >= min && value <= max;
    }

    /**
     * Validates if a value is positive.
     * 
     * @param value the value to validate
     * @return true if positive, false otherwise
     */
    public static boolean isPositive(double value) {
        return value > 0;
    }

    /**
     * Validates if a value is non-negative.
     * 
     * @param value the value to validate
     * @return true if non-negative, false otherwise
     */
    public static boolean isNonNegative(double value) {
        return value >= 0;
    }

    /**
     * Validates if a value is null.
     * 
     * @param value the value to validate
     * @return true if null, false otherwise
     */
    public static boolean isNull(Object value) {
        return value == null;
    }

    /**
     * Validates if a value is not null.
     * 
     * @param value the value to validate
     * @return true if not null, false otherwise
     */
    public static boolean isNotNull(Object value) {
        return value != null;
    }
}
