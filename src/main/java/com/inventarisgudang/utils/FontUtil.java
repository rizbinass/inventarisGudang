package com.inventarisgudang.utils;

import java.awt.*;

/**
 * Font utility class for managing application fonts.
 * Provides consistent font styling throughout the application.
 */
public class FontUtil {

    // Font families
    public static final String FONT_FAMILY_DEFAULT = "Segoe UI";
    public static final String FONT_FAMILY_MONOSPACE = "Consolas";

    // Font sizes
    public static final int SIZE_SMALL = 11;
    public static final int SIZE_NORMAL = 12;
    public static final int SIZE_MEDIUM = 13;
    public static final int SIZE_LARGE = 14;
    public static final int SIZE_TITLE = 16;
    public static final int SIZE_HEADING = 18;
    public static final int SIZE_LARGE_HEADING = 20;

    // Cached fonts
    private static Font defaultFont;
    private static Font boldFont;
    private static Font italicFont;
    private static Font monospacedFont;

    static {
        initializeFonts();
    }

    /**
     * Initializes default fonts.
     */
    private static void initializeFonts() {
        defaultFont = new Font(FONT_FAMILY_DEFAULT, Font.PLAIN, SIZE_NORMAL);
        boldFont = new Font(FONT_FAMILY_DEFAULT, Font.BOLD, SIZE_NORMAL);
        italicFont = new Font(FONT_FAMILY_DEFAULT, Font.ITALIC, SIZE_NORMAL);
        monospacedFont = new Font(FONT_FAMILY_MONOSPACE, Font.PLAIN, SIZE_NORMAL);
    }

    // Private constructor
    private FontUtil() {
    }

    /**
     * Gets the default font.
     * 
     * @return default font
     */
    public static Font getDefaultFont() {
        return defaultFont;
    }

    /**
     * Gets a regular font with specified size.
     * 
     * @param size font size
     * @return font with specified size
     */
    public static Font getFont(int size) {
        return new Font(FONT_FAMILY_DEFAULT, Font.PLAIN, size);
    }

    /**
     * Gets a bold font with specified size.
     * 
     * @param size font size
     * @return bold font with specified size
     */
    public static Font getBoldFont(int size) {
        return new Font(FONT_FAMILY_DEFAULT, Font.BOLD, size);
    }

    /**
     * Gets an italic font with specified size.
     * 
     * @param size font size
     * @return italic font with specified size
     */
    public static Font getItalicFont(int size) {
        return new Font(FONT_FAMILY_DEFAULT, Font.ITALIC, size);
    }

    /**
     * Gets a bold italic font with specified size.
     * 
     * @param size font size
     * @return bold italic font with specified size
     */
    public static Font getBoldItalicFont(int size) {
        return new Font(FONT_FAMILY_DEFAULT, Font.BOLD | Font.ITALIC, size);
    }

    /**
     * Gets a monospaced font with specified size.
     * 
     * @param size font size
     * @return monospaced font with specified size
     */
    public static Font getMonospacedFont(int size) {
        return new Font(FONT_FAMILY_MONOSPACE, Font.PLAIN, size);
    }

    /**
     * Gets a title font (large, bold).
     * 
     * @return title font
     */
    public static Font getTitleFont() {
        return getBoldFont(SIZE_TITLE);
    }

    /**
     * Gets a heading font (large, bold).
     * 
     * @return heading font
     */
    public static Font getHeadingFont() {
        return getBoldFont(SIZE_HEADING);
    }

    /**
     * Gets a large heading font (large, bold).
     * 
     * @return large heading font
     */
    public static Font getLargeHeadingFont() {
        return getBoldFont(SIZE_LARGE_HEADING);
    }

    /**
     * Gets a small label font.
     * 
     * @return small font
     */
    public static Font getSmallFont() {
        return getFont(SIZE_SMALL);
    }

    /**
     * Derives a font from the given font with specified size and style.
     * 
     * @param baseFont the base font
     * @param size the new size
     * @param style the font style (Font.PLAIN, Font.BOLD, Font.ITALIC, etc.)
     * @return derived font
     */
    public static Font deriveFont(Font baseFont, int size, int style) {
        return new Font(baseFont.getFamily(), style, size);
    }
}
