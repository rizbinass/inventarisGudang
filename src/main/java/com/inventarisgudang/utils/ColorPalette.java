package com.inventarisgudang.utils;

import java.awt.*;

/**
 * Color palette for the application.
 * Defines all colors used throughout the application for consistency.
 */
public class ColorPalette {
    
    // Primary Colors
    public static final Color PRIMARY = new Color(0x2196F3);      // Blue
    public static final Color PRIMARY_DARK = new Color(0x1976D2);  // Dark Blue
    public static final Color PRIMARY_LIGHT = new Color(0x64B5F6); // Light Blue

    // Accent Colors
    public static final Color ACCENT = new Color(0xFF9800);        // Orange
    public static final Color ACCENT_DARK = new Color(0xF57C00);   // Dark Orange
    public static final Color ACCENT_LIGHT = new Color(0xFFB74D);  // Light Orange

    // Success Colors
    public static final Color SUCCESS = new Color(0x4CAF50);       // Green
    public static final Color SUCCESS_DARK = new Color(0x388E3C);  // Dark Green
    public static final Color SUCCESS_LIGHT = new Color(0x81C784); // Light Green

    // Warning Colors
    public static final Color WARNING = new Color(0xFFC107);       // Amber
    public static final Color WARNING_DARK = new Color(0xFFA000);  // Dark Amber
    public static final Color WARNING_LIGHT = new Color(0xFFD54F); // Light Amber

    // Error Colors
    public static final Color ERROR = new Color(0xF44336);         // Red
    public static final Color ERROR_DARK = new Color(0xD32F2F);    // Dark Red
    public static final Color ERROR_LIGHT = new Color(0xEF5350);   // Light Red

    // Neutral Colors
    public static final Color BACKGROUND = new Color(0xFAFAFA);    // Light Gray
    public static final Color BACKGROUND_DARK = new Color(0x1E1E1E); // Dark Gray
    public static final Color SURFACE = new Color(0xFFFFFF);       // White
    public static final Color SURFACE_DARK = new Color(0x2E2E2E);  // Dark Surface

    // Text Colors
    public static final Color TEXT_PRIMARY = new Color(0x212121);  // Dark Gray
    public static final Color TEXT_SECONDARY = new Color(0x757575); // Medium Gray
    public static final Color TEXT_DISABLED = new Color(0xBDBDBD);  // Light Gray
    public static final Color TEXT_LIGHT = new Color(0xFFFFFF);    // White

    // Border Colors
    public static final Color BORDER = new Color(0xE0E0E0);        // Light Gray
    public static final Color BORDER_DARK = new Color(0x424242);   // Dark Gray

    // Status Colors
    public static final Color ACTIVE = new Color(0x4CAF50);        // Green
    public static final Color INACTIVE = new Color(0xBDBDBD);      // Gray
    public static final Color PENDING = new Color(0xFF9800);       // Orange

    // Private constructor to prevent instantiation
    private ColorPalette() {
    }

    /**
     * Gets a color with custom alpha/transparency.
     * 
     * @param color the base color
     * @param alpha the alpha value (0-255)
     * @return new Color with specified alpha
     */
    public static Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    /**
     * Gets a color with custom opacity percentage.
     * 
     * @param color the base color
     * @param opacity opacity percentage (0-100)
     * @return new Color with specified opacity
     */
    public static Color withOpacity(Color color, double opacity) {
        int alpha = (int) (255 * (opacity / 100.0));
        return withAlpha(color, Math.max(0, Math.min(255, alpha)));
    }
}
