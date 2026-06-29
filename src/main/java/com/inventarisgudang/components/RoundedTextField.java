package com.inventarisgudang.components;

import com.inventarisgudang.utils.UIConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Custom rounded text field component with modern ERP styling.
 */
public class RoundedTextField extends JTextField {
    private final int arcWidth;
    private final int arcHeight;
    private final int padding;
    private boolean isFocused = false;

    public RoundedTextField(int arcWidth, int arcHeight, int padding) {
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.padding = padding;

        setFont(UIConstants.createFont(UIConstants.FONT_BASE));
        setBackground(UIConstants.INPUT_BG);
        setForeground(UIConstants.TEXT_PRIMARY);
        setCaretColor(UIConstants.PRIMARY);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.INPUT_BORDER, 1),
                BorderFactory.createEmptyBorder(padding, padding, padding, padding)
        ));
        setPreferredSize(new Dimension(250, UIConstants.INPUT_HEIGHT));
        setMargin(new Insets(UIConstants.INPUT_PADDING, UIConstants.INPUT_PADDING, 
                            UIConstants.INPUT_PADDING, UIConstants.INPUT_PADDING));

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

    public RoundedTextField() {
        this(UIConstants.CORNER_RADIUS_NORMAL, UIConstants.CORNER_RADIUS_NORMAL, UIConstants.INPUT_PADDING);
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
