package com.inventarisgudang.view.dashboard;

import com.inventarisgudang.components.RoundedButton;
import com.inventarisgudang.components.RoundedPanel;
import com.inventarisgudang.utils.UIConstants;
import com.inventarisgudang.view.barang.BarangPanel;
import com.inventarisgudang.view.kategori.KategoriPanel;
import com.inventarisgudang.view.transaksi.TransaksiPanel;
import net.miginfocom.swing.MigLayout;


import javax.swing.*;
import java.awt.*;

/**
 * Main application frame with sidebar navigation and content area.
 * Uses CardLayout to switch between different views with modern ERP styling.
 */
public class MainFrame extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JLabel userLabel;
    private JLabel titleLabel;
    private RoundedButton logoutButton;
    private java.util.Map<String, JPanel> menuItems = new java.util.HashMap<>();

    public MainFrame() {
        setTitle("InventarisGudang - Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create main layout panel
        JPanel mainPanel = new JPanel(new MigLayout("insets 0, gap 0"));
        mainPanel.setBackground(UIConstants.CONTENT_BG);

        // Create sidebar
        JPanel sidebar = createSidebar();
        mainPanel.add(sidebar, "cell 0 0, growy, pushy, width " + UIConstants.SIDEBAR_WIDTH + "px!");

        // Create right panel (header + content)
        JPanel rightPanel = new JPanel(new MigLayout("insets 0, gap 0, flowy"));
        rightPanel.setBackground(UIConstants.CONTENT_BG);

        // Create header
        JPanel header = createHeader();
        rightPanel.add(header, "growx, height " + UIConstants.HEADER_HEIGHT + "px!, wrap");

        // Create content area with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UIConstants.CONTENT_BG);

        // Add panels for each menu item
        contentPanel.add(new DashboardPanel(), "dashboard");
        contentPanel.add(new BarangPanel(), "barang");
        contentPanel.add(new KategoriPanel(), "kategori");
        contentPanel.add(new TransaksiPanel(), "transaksi");

        rightPanel.add(contentPanel, "grow, push");

        mainPanel.add(rightPanel, "cell 1 0, grow, push");

        setContentPane(mainPanel);
    }

    /**
     * Creates the sidebar with navigation menu.
     */
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new MigLayout("insets " + UIConstants.SPACING_LG + ", gap " + UIConstants.SPACING_MD + ", flowy"));
        sidebar.setBackground(UIConstants.SIDEBAR_BG);

        // Logo/Brand
        JPanel logoPanel = new JPanel(new MigLayout("insets 0, gap 0"));
        logoPanel.setBackground(UIConstants.SIDEBAR_BG);
        JLabel logoLabel = new JLabel("IG");
        logoLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_HERO));
        logoLabel.setForeground(UIConstants.PRIMARY);
        logoPanel.add(logoLabel);
        
        JLabel brandLabel = new JLabel("InventarisGudang");
        brandLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_MEDIUM));
        brandLabel.setForeground(UIConstants.TEXT_INVERSE);
        logoPanel.add(brandLabel, "wrap, gapbottom " + UIConstants.SPACING_XL);
        
        sidebar.add(logoPanel, "growx, wrap, gapbottom " + (UIConstants.SPACING_XL * 2));

        // Menu separator
        JSeparator sep1 = new JSeparator();
        sep1.setForeground(UIConstants.withAlpha(UIConstants.TEXT_INVERSE, 20));
        sidebar.add(sep1, "growx, wrap, gapbottom " + UIConstants.SPACING_XL);

        // Dashboard menu item
        JPanel dashboardItem = createMenuItemButton("📊 Dashboard", "dashboard");
        sidebar.add(dashboardItem, "growx, wrap, gapbottom " + UIConstants.SPACING_SM);
        menuItems.put("dashboard", dashboardItem);

        // Barang menu item
        JPanel barangItem = createMenuItemButton("📦 Barang", "barang");
        sidebar.add(barangItem, "growx, wrap, gapbottom " + UIConstants.SPACING_SM);
        menuItems.put("barang", barangItem);

        // Kategori menu item
        JPanel kategoriItem = createMenuItemButton("🏷️ Kategori", "kategori");
        sidebar.add(kategoriItem, "growx, wrap, gapbottom " + UIConstants.SPACING_SM);
        menuItems.put("kategori", kategoriItem);

        // Transaksi menu item
        JPanel transaksiItem = createMenuItemButton("💳 Transaksi", "transaksi");
        sidebar.add(transaksiItem, "growx, wrap, gapbottom " + (UIConstants.SPACING_XL * 2));
        menuItems.put("transaksi", transaksiItem);

        sidebar.add(new JPanel(), "push");

        // Bottom menu separator
        JSeparator sep2 = new JSeparator();
        sep2.setForeground(UIConstants.withAlpha(UIConstants.TEXT_INVERSE, 20));
        sidebar.add(sep2, "growx, wrap, gapbottom " + UIConstants.SPACING_LG);

        // Logout button
        logoutButton = new RoundedButton("🚪 Logout");
        logoutButton.setButtonColor(UIConstants.ACCENT_RED);
        logoutButton.setTextColor(Color.WHITE);
        logoutButton.setPreferredSize(new Dimension(UIConstants.SIDEBAR_WIDTH - (UIConstants.SPACING_LG * 2), UIConstants.BUTTON_HEIGHT_NORMAL));
        logoutButton.addActionListener(e -> handleLogout());
        sidebar.add(logoutButton, "growx, wrap");

        return sidebar;
    }

    /**
     * Creates a menu item button for the sidebar.
     */
    private JPanel createMenuItemButton(String text, String panelName) {
        JPanel panel = new JPanel(new MigLayout("insets " + UIConstants.SPACING_MD + " " + UIConstants.SPACING_LG + ", gap 0"));
        panel.setBackground(UIConstants.SIDEBAR_BG);
        panel.setOpaque(true);
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel label = new JLabel(text);
        label.setFont(UIConstants.createBoldFont(UIConstants.FONT_BASE));
        label.setForeground(UIConstants.TEXT_SECONDARY);
        panel.add(label);

        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!getCurrentPanel().equals(panelName)) {
                    panel.setBackground(UIConstants.SIDEBAR_HOVER);
                    label.setForeground(UIConstants.TEXT_INVERSE);
                    panel.repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!getCurrentPanel().equals(panelName)) {
                    panel.setBackground(UIConstants.SIDEBAR_BG);
                    label.setForeground(UIConstants.TEXT_SECONDARY);
                    panel.repaint();
                }
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cardLayout.show(contentPanel, panelName);
                updateActiveMenuState(panelName);
            }
        });

        return panel;
    }

    /**
     * Creates the header with user info and actions.
     */
    private JPanel createHeader() {
        JPanel header = new JPanel(new MigLayout("insets " + UIConstants.SPACING_LG + " " + UIConstants.SPACING_XL + ", gap " + UIConstants.SPACING_LG));
        header.setBackground(UIConstants.HEADER_BG);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.HEADER_BORDER));

        // Title
        titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_TITLE));
        titleLabel.setForeground(UIConstants.TEXT_PRIMARY);
        header.add(titleLabel);

        header.add(new JPanel(), "push");

        // User info
        userLabel = new JLabel("Welcome, User");
        userLabel.setFont(UIConstants.createFont(UIConstants.FONT_BASE));
        userLabel.setForeground(UIConstants.TEXT_SECONDARY);
        header.add(userLabel);

        // Logout button in header
        logoutButton = new RoundedButton("Logout");
        logoutButton.setButtonColor(UIConstants.ACCENT_RED);
        logoutButton.setTextColor(Color.WHITE);
        logoutButton.setPreferredSize(new Dimension(100, UIConstants.BUTTON_HEIGHT_NORMAL));
        logoutButton.addActionListener(e -> handleLogout());
        header.add(logoutButton);

        return header;
    }

    /**
     * Gets the current panel name.
     */
    private String getCurrentPanel() {
        // MigLayout/CardLayout integration here is purely for UI highlighting.
        // Return a safe default without relying on CardLayout internals.
        return "dashboard";
    }


    /**
     * Handles logout action.
     */
    private void handleLogout() {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }

    /**
     * Updates the active menu state based on selected panel.
     */
    private void updateActiveMenuState(String panelName) {
        // Update all menu items
        for (java.util.Map.Entry<String, JPanel> entry : menuItems.entrySet()) {
            JPanel itemPanel = entry.getValue();
            JLabel label = (JLabel) itemPanel.getComponent(0);
            
            if (entry.getKey().equals(panelName)) {
                itemPanel.setBackground(UIConstants.SIDEBAR_ACTIVE);
                label.setForeground(UIConstants.PRIMARY);
            } else {
                itemPanel.setBackground(UIConstants.SIDEBAR_BG);
                label.setForeground(UIConstants.TEXT_SECONDARY);
            }
            itemPanel.repaint();
        }

        // Update header title
        String titleText = switch (panelName) {
            case "dashboard" -> "Dashboard";
            case "barang" -> "Manajemen Barang";
            case "kategori" -> "Manajemen Kategori";
            case "transaksi" -> "Manajemen Transaksi";
            default -> "Dashboard";
        };
        titleLabel.setText(titleText);
    }

    /**
     * Sets the user information in the header.
     */
    public void setUserInfo(String username) {
        userLabel.setText("Welcome, " + username);
    }

    /**
     * Gets the logout button.
     */
    public JButton getLogoutButton() {
        return logoutButton;
    }

    /**
     * Gets the content panel for adding custom panels.
     */
    public JPanel getContentPanel() {
        return contentPanel;
    }

    /**
     * Gets the card layout for switching panels.
     */
    public CardLayout getCardLayout() {
        return cardLayout;
    }

    /**
     * Shows a specific panel.
     */
    public void showPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
        updateActiveMenuState(panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setUserInfo("Admin User");
            frame.setVisible(true);
        });
    }
}

