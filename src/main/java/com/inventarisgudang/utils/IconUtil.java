package com.inventarisgudang.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.net.URL;

/**
 * Icon and image utility class.
 * Provides methods for loading and managing icons throughout the application.
 */
public class IconUtil {
    private static final Logger logger = LoggerFactory.getLogger(IconUtil.class);

    // Private constructor
    private IconUtil() {
    }

    /**
     * Loads an icon from the resources folder.
     * 
     * @param resourcePath the path to the resource (e.g., "icons/add.png")
     * @return ImageIcon loaded from the resource, or null if not found
     */
    public static ImageIcon loadIcon(String resourcePath) {
        try {
            URL imageUrl = IconUtil.class.getResource("/" + resourcePath);
            if (imageUrl != null) {
                return new ImageIcon(imageUrl);
            } else {
                logger.warn("Icon resource not found: {}", resourcePath);
                return null;
            }
        } catch (Exception ex) {
            logger.error("Failed to load icon: {}", resourcePath, ex);
            return null;
        }
    }

    /**
     * Loads an icon from the resources folder and scales it to the specified size.
     * 
     * @param resourcePath the path to the resource
     * @param width the desired width
     * @param height the desired height
     * @return scaled ImageIcon, or null if not found
     */
    public static ImageIcon loadIcon(String resourcePath, int width, int height) {
        ImageIcon icon = loadIcon(resourcePath);
        if (icon != null) {
            return scaleIcon(icon, width, height);
        }
        return null;
    }

    /**
     * Scales an ImageIcon to the specified dimensions.
     * 
     * @param icon the icon to scale
     * @param width the desired width
     * @param height the desired height
     * @return scaled ImageIcon
     */
    public static ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Creates a simple colored square icon.
     * 
     * @param color the color of the icon
     * @param size the size of the icon
     * @return ImageIcon with the specified color
     */
    public static ImageIcon createColoredIcon(Color color, int size) {
        Image image = new BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, size, size);
        g2d.dispose();
        return new ImageIcon(image);
    }

    /**
     * Creates a circular colored icon.
     * 
     * @param color the color of the icon
     * @param size the size of the icon
     * @return ImageIcon with a circular shape
     */
    public static ImageIcon createCircleIcon(Color color, int size) {
        java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(
                size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.fillOval(0, 0, size, size);
        g2d.dispose();
        return new ImageIcon(image);
    }

    /**
     * Creates a standard button icon.
     * 
     * @param size the size of the icon
     * @return default button icon
     */
    public static ImageIcon createDefaultIcon(int size) {
        return createColoredIcon(ColorPalette.PRIMARY, size);
    }

    /**
     * Creates an error/warning icon.
     * 
     * @param size the size of the icon
     * @return error icon
     */
    public static ImageIcon createErrorIcon(int size) {
        return createColoredIcon(ColorPalette.ERROR, size);
    }

    /**
     * Creates a success icon.
     * 
     * @param size the size of the icon
     * @return success icon
     */
    public static ImageIcon createSuccessIcon(int size) {
        return createColoredIcon(ColorPalette.SUCCESS, size);
    }

    /**
     * Creates a warning icon.
     * 
     * @param size the size of the icon
     * @return warning icon
     */
    public static ImageIcon createWarningIcon(int size) {
        return createColoredIcon(ColorPalette.WARNING, size);
    }

    /**
     * Creates an information icon.
     * 
     * @param size the size of the icon
     * @return information icon
     */
    public static ImageIcon createInfoIcon(int size) {
        return createColoredIcon(ColorPalette.PRIMARY, size);
    }
}
