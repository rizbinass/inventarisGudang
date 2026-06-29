package com.inventarisgudang;

import com.formdev.flatlaf.FlatDarkLaf;
import com.inventarisgudang.utils.UIConstants;
import com.inventarisgudang.view.dashboard.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import javax.swing.*;


/**
 * Application entry point for InventarisGudang.
 * Initializes the application and launches the main window.
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting InventarisGudang Application...");
        
        try {
            // Set FlatLaf as the look and feel with dark theme
            UIManager.setLookAndFeel(new FlatDarkLaf());
            
            // Customize FlatLaf colors for modern ERP aesthetic
            UIManager.put("Component.focusWidth", 2);
            UIManager.put("Component.focusedBorderColor", UIConstants.BORDER_FOCUS);
            UIManager.put("Component.borderColor", UIConstants.BORDER_LIGHT);
            UIManager.put("TextField.background", UIConstants.INPUT_BG);
            UIManager.put("TextField.foreground", UIConstants.TEXT_PRIMARY);
            UIManager.put("Button.background", UIConstants.PRIMARY);
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Table.background", UIConstants.CARD_BG);
            UIManager.put("Table.foreground", UIConstants.TEXT_PRIMARY);
            UIManager.put("Table.alternateRowColor", UIConstants.TABLE_ROW_HOVER);
            
            logger.info("FlatLaf theme applied successfully");
        } catch (UnsupportedLookAndFeelException ex) {
            logger.error("Failed to set FlatLaf look and feel", ex);
        }

        // Schedule the GUI to be created on the EDT
        SwingUtilities.invokeLater(() -> {
            logger.info("Initializing main application window");
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}

