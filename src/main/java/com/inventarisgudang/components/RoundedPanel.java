package com.inventarisgudang.components;

import com.inventarisgudang.utils.UIConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Custom rounded panel component with modern ERP styling.
 */
public class RoundedPanel extends JPanel {
    private final int arcWidth;
    private final int arcHeight;
    private Color backgroundColor;
    private Color borderColor;
    private float borderStroke;
    private boolean hasShadow;

    public RoundedPanel(int arcWidth, int arcHeight) {
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.backgroundColor = UIConstants.CARD_BG;
        this.borderColor = UIConstants.BORDER_LIGHT;
        this.borderStroke = UIConstants.STROKE_NORMAL;
        this.hasShadow = true;

        setOpaque(false);
        setLayout(new BorderLayout());
    }

    public RoundedPanel(int arcWidth, int arcHeight, Color backgroundColor) {
        this(arcWidth, arcHeight);
        setBackgroundColor(backgroundColor);
    }

    public RoundedPanel() {
        this(UIConstants.CORNER_RADIUS_NORMAL, UIConstants.CORNER_RADIUS_NORMAL);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw shadow if enabled
        if (hasShadow) {
            g2d.setColor(UIConstants.withAlpha(Color.BLACK, 10));
            g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, arcWidth, arcHeight);
        }

        // Draw background
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

        // Draw border
        if (borderStroke > 0) {
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(borderStroke));
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        }

        super.paintComponent(g);
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    public void setBorderStroke(float stroke) {
        this.borderStroke = stroke;
        repaint();
    }

    public void setShadow(boolean shadow) {
        this.hasShadow = shadow;
        repaint();
    }

    public void setBorderProperties(Color color, int thickness) {
        this.borderColor = color;
        this.borderStroke = thickness;
        repaint();
    }
}
