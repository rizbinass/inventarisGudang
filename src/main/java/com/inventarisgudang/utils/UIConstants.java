package com.inventarisgudang.utils;

import java.awt.*;

/**
 * Centralized UI constants for consistent styling across the application.
 * Defines modern ERP-style colors, fonts, spacing, and dimensions.
 */
public class UIConstants {

    // ==================== COLORS ====================

    // Primary Colors
    public static final Color PRIMARY = new Color(0x2563EB);      // Bright Blue
    public static final Color PRIMARY_DARK = new Color(0x1E40AF);  // Dark Blue
    public static final Color PRIMARY_LIGHT = new Color(0xDEEBF7); // Light Blue

    // Secondary Colors
    public static final Color SECONDARY = new Color(0x7C3AED);    // Purple
    public static final Color SECONDARY_DARK = new Color(0x5B21B6); // Dark Purple
    public static final Color SECONDARY_LIGHT = new Color(0xEDE9FE); // Light Purple

    // Accent Colors
    public static final Color ACCENT_GREEN = new Color(0x10B981);  // Success Green
    public static final Color ACCENT_ORANGE = new Color(0xF59E0B); // Warning Orange
    public static final Color ACCENT_RED = new Color(0xEF4444);    // Error Red
    public static final Color ACCENT_CYAN = new Color(0x06B6D4);   // Info Cyan

    // Neutrals
    public static final Color SIDEBAR_BG = new Color(0x0F172A);    // Very Dark Navy
    public static final Color SIDEBAR_HOVER = new Color(0x1E293B); // Dark Slate
    public static final Color SIDEBAR_ACTIVE = new Color(0x334155); // Medium Slate

    public static final Color HEADER_BG = new Color(0xFFFFFF);     // White
    public static final Color HEADER_BORDER = new Color(0xE2E8F0);  // Light Gray

    public static final Color CONTENT_BG = new Color(0xF8FAFC);    // Light Blue-Gray
    public static final Color CARD_BG = new Color(0xFFFFFF);       // White
    public static final Color PANEL_BG = new Color(0xF1F5F9);      // Lighter Blue-Gray

    public static final Color TEXT_PRIMARY = new Color(0x0F172A);  // Very Dark Navy
    public static final Color TEXT_SECONDARY = new Color(0x475569); // Medium Gray
    public static final Color TEXT_TERTIARY = new Color(0x94A3B8);  // Light Gray
    public static final Color TEXT_DISABLED = new Color(0xCBD5E1);  // Lighter Gray
    public static final Color TEXT_INVERSE = new Color(0xF1F5F9);  // Light for dark backgrounds

    public static final Color BORDER_LIGHT = new Color(0xE2E8F0);  // Light Border
    public static final Color BORDER_MEDIUM = new Color(0xCBD5E1); // Medium Border
    public static final Color BORDER_FOCUS = new Color(0x2563EB);  // Focus Blue

    public static final Color TABLE_HEADER_BG = new Color(0xF1F5F9);
    public static final Color TABLE_ROW_HOVER = new Color(0xF8FAFC);
    public static final Color TABLE_ROW_SELECTED = new Color(0xDEEBF7);
    public static final Color TABLE_BORDER = new Color(0xE2E8F0);

    public static final Color INPUT_BG = new Color(0xFFFFFF);
    public static final Color INPUT_BG_FOCUS = new Color(0xF0F9FF);
    public static final Color INPUT_BORDER = new Color(0xCBD5E1);

    // ==================== FONTS ====================

    public static final String FONT_FAMILY = "Segoe UI";
    public static final String FONT_MONOSPACE = "Consolas";

    // Font sizes
    public static final int FONT_TINY = 9;
    public static final int FONT_SMALL = 10;
    public static final int FONT_BASE = 11;
    public static final int FONT_MEDIUM = 12;
    public static final int FONT_LARGE = 13;
    public static final int FONT_XL = 14;
    public static final int FONT_XXL = 16;
    public static final int FONT_HEADING = 18;
    public static final int FONT_TITLE = 20;
    public static final int FONT_LARGE_TITLE = 24;
    public static final int FONT_HERO = 28;

    // ==================== SPACING ====================

    public static final int SPACING_XS = 4;
    public static final int SPACING_SM = 8;
    public static final int SPACING_MD = 12;
    public static final int SPACING_LG = 16;
    public static final int SPACING_XL = 20;
    public static final int SPACING_2XL = 24;
    public static final int SPACING_3XL = 32;

    public static final int PADDING_COMPACT = 8;
    public static final int PADDING_NORMAL = 12;
    public static final int PADDING_COMFORTABLE = 16;
    public static final int PADDING_LARGE = 20;

    // ==================== COMPONENT DIMENSIONS ====================

    public static final int BUTTON_HEIGHT_SMALL = 32;
    public static final int BUTTON_HEIGHT_NORMAL = 38;
    public static final int BUTTON_HEIGHT_LARGE = 44;

    public static final int BUTTON_PADDING_H = 16;
    public static final int BUTTON_PADDING_V = 10;

    public static final int INPUT_HEIGHT = 38;
    public static final int INPUT_PADDING = 10;

    public static final int ICON_SMALL = 16;
    public static final int ICON_MEDIUM = 20;
    public static final int ICON_LARGE = 24;
    public static final int ICON_XL = 32;

    public static final int CORNER_RADIUS_SMALL = 8;
    public static final int CORNER_RADIUS_NORMAL = 12;
    public static final int CORNER_RADIUS_LARGE = 16;

    public static final int TABLE_ROW_HEIGHT = 32;
    public static final int TABLE_HEADER_HEIGHT = 36;

    public static final int SIDEBAR_WIDTH = 280;
    public static final int HEADER_HEIGHT = 70;

    // ==================== BORDERS & STROKES ====================

    public static final float STROKE_THIN = 0.5f;
    public static final float STROKE_NORMAL = 1.0f;
    public static final float STROKE_FOCUS = 2.0f;

    // ==================== HELPER METHODS ====================

    /**
     * Creates a font with the specified size.
     */
    public static Font createFont(int size) {
        return new Font(FONT_FAMILY, Font.PLAIN, size);
    }

    /**
     * Creates a bold font with the specified size.
     */
    public static Font createBoldFont(int size) {
        return new Font(FONT_FAMILY, Font.BOLD, size);
    }

    /**
     * Creates a color with transparency.
     */
    public static Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    /**
     * Creates a color with opacity percentage.
     */
    public static Color withOpacity(Color color, double opacity) {
        int alpha = (int) (255 * (opacity / 100.0));
        return withAlpha(color, Math.max(0, Math.min(255, alpha)));
    }
}
