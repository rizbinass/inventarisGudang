package com.inventarisgudang.utils;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Utility class for modern table styling.
 */
public class TableStyleUtil {

    /**
     * Apply modern styling to a JTable.
     */
    public static void styleTable(JTable table) {
        // Row height
        table.setRowHeight(UIConstants.TABLE_ROW_HEIGHT);
        
        // Font
        table.setFont(UIConstants.createFont(UIConstants.FONT_BASE));
        table.getTableHeader().setFont(UIConstants.createBoldFont(UIConstants.FONT_MEDIUM));
        
        // Colors
        table.setBackground(UIConstants.CARD_BG);
        table.setForeground(UIConstants.TEXT_PRIMARY);
        table.setSelectionBackground(UIConstants.TABLE_ROW_SELECTED);
        table.setSelectionForeground(UIConstants.TEXT_PRIMARY);
        table.setGridColor(UIConstants.TABLE_BORDER);
        table.setShowGrid(true);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        
        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setBackground(UIConstants.TABLE_HEADER_BG);
        header.setForeground(UIConstants.TEXT_PRIMARY);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.TABLE_BORDER));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, UIConstants.TABLE_HEADER_HEIGHT));
        
        // Set header renderer
        header.setDefaultRenderer(createHeaderRenderer());
        
        // Alternating row colors
        table.setDefaultRenderer(Object.class, createCellRenderer());
    }

    /**
     * Create a modern table header renderer.
     */
    private static TableCellRenderer createHeaderRenderer() {
        return new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                          boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value != null ? value.toString() : "");
                label.setFont(UIConstants.createBoldFont(UIConstants.FONT_MEDIUM));
                label.setForeground(UIConstants.TEXT_PRIMARY);
                label.setBackground(UIConstants.TABLE_HEADER_BG);
                label.setOpaque(true);
                label.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                return label;
            }
        };
    }

    /**
     * Create a modern table cell renderer with alternating row colors.
     */
    private static TableCellRenderer createCellRenderer() {
        return new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                          boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value != null ? value.toString() : "");
                label.setFont(UIConstants.createFont(UIConstants.FONT_BASE));
                label.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
                
                if (isSelected) {
                    label.setBackground(UIConstants.TABLE_ROW_SELECTED);
                    label.setForeground(UIConstants.TEXT_PRIMARY);
                } else if (row % 2 == 0) {
                    label.setBackground(UIConstants.CARD_BG);
                    label.setForeground(UIConstants.TEXT_PRIMARY);
                } else {
                    label.setBackground(UIConstants.TABLE_ROW_HOVER);
                    label.setForeground(UIConstants.TEXT_PRIMARY);
                }
                
                label.setOpaque(true);
                return label;
            }
        };
    }
}
