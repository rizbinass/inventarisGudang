package com.inventarisgudang.components;

import com.inventarisgudang.utils.UIConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Custom rounded combo box component with modern ERP styling.
 */
public class RoundedComboBox extends JComboBox<String> {
    private final int arcWidth;
    private final int arcHeight;
    private boolean isFocused = false;

    public RoundedComboBox(String[] items, int arcWidth, int arcHeight) {
        super(items);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;

        setFont(UIConstants.createFont(UIConstants.FONT_BASE));
        setBackground(UIConstants.INPUT_BG);
        setForeground(UIConstants.TEXT_PRIMARY);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.INPUT_BORDER, 1),
                BorderFactory.createEmptyBorder(UIConstants.INPUT_PADDING, UIConstants.INPUT_PADDING, 
                                              UIConstants.INPUT_PADDING, UIConstants.INPUT_PADDING)
        ));
        setPreferredSize(new Dimension(250, UIConstants.INPUT_HEIGHT));

        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                isFocused = true;
                repaint();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                isFocused = false;
                repaint();
            }
        });
    }

    public RoundedComboBox(String[] items) {
        this(items, UIConstants.CORNER_RADIUS_NORMAL, UIConstants.CORNER_RADIUS_NORMAL);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw rounded background
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (isFocused) {
            g2d.setColor(UIConstants.BORDER_FOCUS);
            g2d.setStroke(new BasicStroke(UIConstants.STROKE_FOCUS));
        } else {
            g2d.setColor(UIConstants.BORDER_LIGHT);
            g2d.setStroke(new BasicStroke(UIConstants.STROKE_NORMAL));
        }

        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
    }
}
