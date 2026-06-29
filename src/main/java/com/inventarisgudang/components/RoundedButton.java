package com.inventarisgudang.components;

import com.inventarisgudang.utils.UIConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Custom rounded button component with modern ERP styling.
 */
public class RoundedButton extends JButton {
    private final int arcWidth;
    private final int arcHeight;
    private Color buttonColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color textColor;
    private boolean isHovered = false;
    private boolean isPressed = false;
    private boolean isEnabled = true;

    public RoundedButton(String text, int arcWidth, int arcHeight) {
        super(text);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.buttonColor = UIConstants.PRIMARY;
        this.hoverColor = UIConstants.PRIMARY_DARK;
        this.pressedColor = new Color(0x1E3A8A);
        this.textColor = Color.WHITE;
        
        initializeButton();
    }

    public RoundedButton(String text) {
        this(text, UIConstants.CORNER_RADIUS_NORMAL, UIConstants.CORNER_RADIUS_NORMAL);
    }

    private void initializeButton() {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(textColor);
        setFont(UIConstants.createBoldFont(UIConstants.FONT_MEDIUM));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorderPainted(false);
        setOpaque(false);
        setPreferredSize(new Dimension(UIConstants.BUTTON_PADDING_H * 2 + 60, UIConstants.BUTTON_HEIGHT_NORMAL));
        setMargin(new Insets(UIConstants.BUTTON_PADDING_V, UIConstants.BUTTON_PADDING_H, 
                            UIConstants.BUTTON_PADDING_V, UIConstants.BUTTON_PADDING_H));

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (isEnabled()) {
                    isHovered = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                isHovered = false;
                isPressed = false;
                repaint();
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (isEnabled()) {
                    isPressed = true;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                isPressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Color currentColor = buttonColor;
        if (!isEnabled()) {
            currentColor = UIConstants.TEXT_DISABLED;
        } else if (isPressed) {
            currentColor = pressedColor;
        } else if (isHovered) {
            currentColor = hoverColor;
        }

        // Draw background
        g2d.setColor(currentColor);
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

        // Draw shadow/depth on hover
        if (isHovered && !isPressed) {
            g2d.setColor(UIConstants.withAlpha(Color.BLACK, 10));
            g2d.fillRoundRect(2, 2, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        }

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Border not needed for modern look
    }

    public void setButtonColor(Color color) {
        this.buttonColor = color;
        this.hoverColor = new Color(Math.max(0, color.getRed() - 20), 
                                   Math.max(0, color.getGreen() - 20), 
                                   Math.max(0, color.getBlue() - 20));
        repaint();
    }

    public void setTextColor(Color color) {
        this.textColor = color;
        setForeground(color);
        repaint();
    }
}
