package com.inventarisgudang.view.login;

import com.inventarisgudang.components.RoundedButton;
import com.inventarisgudang.components.RoundedPasswordField;
import com.inventarisgudang.components.RoundedTextField;
import net.miginfocom.swing.MigLayout;


import javax.swing.*;
import java.awt.*;

/**
 * Login frame for application authentication.
 * Modern UI with FlatLaf and MigLayout.
 */
public class LoginFrame extends JFrame {
    private RoundedTextField emailField;
    private RoundedPasswordField passwordField;
    private RoundedButton loginButton;
    private JLabel registerLabel;

    public LoginFrame() {
        setTitle("InventarisGudang - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main content panel
        JPanel mainPanel = new JPanel(new MigLayout("insets 0, gap 0"));
        mainPanel.setBackground(new Color(0xFFFFFF));

        // Create sidebar
        JPanel sidebar = createSidebar();
        mainPanel.add(sidebar, "cell 0 0, growy, pushy, width 300px");

        // Create login form panel
        JPanel loginPanel = createLoginPanel();
        mainPanel.add(loginPanel, "cell 1 0, grow, push");

        setContentPane(mainPanel);
    }

    /**
     * Creates the sidebar panel.
     */
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new MigLayout("insets 40, gap 20, center, flowy"));
        sidebar.setBackground(new Color(0x1E293B)); // Sidebar color

        // Logo/Title
        JLabel titleLabel = new JLabel("InventarisGudang");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0xFFFFFF));
        sidebar.add(titleLabel, "center, wrap");

        // Subtitle
        JLabel subtitleLabel = new JLabel("Warehouse Management System");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(0xCBD5E1));
        sidebar.add(subtitleLabel, "center, wrap, gapbottom 60");

        // Feature list
        String[] features = {
            "✓ Real-time Inventory Tracking",
            "✓ Product Management",
            "✓ Transaction Monitoring",
            "✓ Category Organization"
        };

        for (String feature : features) {
            JLabel featureLabel = new JLabel(feature);
            featureLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            featureLabel.setForeground(new Color(0xE2E8F0));
            sidebar.add(featureLabel, "left, wrap, gapbottom 15");
        }

        sidebar.add(new JPanel(), "push");

        // Footer
        JLabel versionLabel = new JLabel("Version 1.0.0");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        versionLabel.setForeground(new Color(0x64748B));
        sidebar.add(versionLabel, "center, wrap");

        return sidebar;
    }

    /**
     * Creates the login form panel.
     */
    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new MigLayout("insets 60, gap 20, flowy"));
        loginPanel.setBackground(new Color(0xFFFFFF));

        // Header
        JLabel welcomeLabel = new JLabel("Welcome Back");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(0x1E293B));
        loginPanel.add(welcomeLabel, "wrap, gapbottom 10");

        JLabel subtitleLabel = new JLabel("Sign in to your account to continue");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(0x64748B));
        loginPanel.add(subtitleLabel, "wrap, gapbottom 40");

        // Email label
        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        emailLabel.setForeground(new Color(0x1E293B));
        loginPanel.add(emailLabel, "wrap, gapbottom 8");

        // Email field
        emailField = new RoundedTextField();
        emailField.setToolTipText("Enter your email");
        loginPanel.add(emailField, "wrap, gapbottom 20, width 300px");

        // Password label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        passwordLabel.setForeground(new Color(0x1E293B));
        loginPanel.add(passwordLabel, "wrap, gapbottom 8");

        // Password field
        passwordField = new RoundedPasswordField();
        passwordField.setToolTipText("Enter your password");
        loginPanel.add(passwordField, "wrap, gapbottom 30, width 300px");

        // Remember me checkbox
        JCheckBox rememberCheckBox = new JCheckBox("Remember me");
        rememberCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        rememberCheckBox.setForeground(new Color(0x64748B));
        rememberCheckBox.setBackground(new Color(0xFFFFFF));
        loginPanel.add(rememberCheckBox, "wrap, gapbottom 30");

        // Login button
        loginButton = new RoundedButton("Login", 20, 20);
        loginButton.setPreferredSize(new Dimension(300, 45));
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loginPanel.add(loginButton, "wrap, gapbottom 20, width 300px, height 45px");

        // Register link
        JPanel registerPanel = new JPanel(new MigLayout("insets 0, gap 5"));
        registerPanel.setBackground(new Color(0xFFFFFF));

        JLabel dontHaveLabel = new JLabel("Don't have an account?");
        dontHaveLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        dontHaveLabel.setForeground(new Color(0x64748B));
        registerPanel.add(dontHaveLabel);

        registerLabel = new JLabel("Register here");
        registerLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        registerLabel.setForeground(new Color(0x2563EB)); // Primary color
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerPanel.add(registerLabel);

        loginPanel.add(registerPanel, "wrap");

        loginPanel.add(new JPanel(), "push");

        return loginPanel;
    }

    /**
     * Gets the email field.
     */
    public JTextField getEmailField() {
        return emailField;
    }

    /**
     * Gets the password field.
     */
    public JPasswordField getPasswordField() {
        return passwordField;
    }

    /**
     * Gets the login button.
     */
    public RoundedButton getLoginButton() {
        return loginButton;
    }

    /**
     * Gets the register label.
     */
    public JLabel getRegisterLabel() {
        return registerLabel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}
