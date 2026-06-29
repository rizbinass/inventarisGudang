package com.inventarisgudang.view.login;

import com.inventarisgudang.components.RoundedButton;
import com.inventarisgudang.components.RoundedComboBox;
import com.inventarisgudang.components.RoundedPasswordField;
import com.inventarisgudang.components.RoundedTextField;
import net.miginfocom.swing.MigLayout;


import javax.swing.*;
import java.awt.*;

/**
 * Register frame for new user registration.
 * Modern UI with FlatLaf and MigLayout.
 */
public class RegisterFrame extends JFrame {
    private RoundedTextField namaField;
    private RoundedTextField usernameField;
    private RoundedPasswordField passwordField;
    private RoundedPasswordField confirmPasswordField;
    private RoundedComboBox roleComboBox;
    private RoundedButton registerButton;
    private RoundedButton backButton;

    public RegisterFrame() {
        setTitle("InventarisGudang - Register");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main content panel
        JPanel mainPanel = new JPanel(new MigLayout("insets 0, gap 0"));
        mainPanel.setBackground(new Color(0xFFFFFF));

        // Create sidebar
        JPanel sidebar = createSidebar();
        mainPanel.add(sidebar, "cell 0 0, growy, pushy, width 300px");

        // Create registration form panel
        JPanel registerPanel = createRegisterPanel();
        mainPanel.add(registerPanel, "cell 1 0, grow, push");

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
        JLabel subtitleLabel = new JLabel("Create Your Account");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(0xCBD5E1));
        sidebar.add(subtitleLabel, "center, wrap, gapbottom 60");

        // Information list
        String[] information = {
            "✓ Quick Registration",
            "✓ Secure Access",
            "✓ Full Features Available",
            "✓ 24/7 Support"
        };

        for (String info : information) {
            JLabel infoLabel = new JLabel(info);
            infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            infoLabel.setForeground(new Color(0xE2E8F0));
            sidebar.add(infoLabel, "left, wrap, gapbottom 15");
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
     * Creates the registration form panel.
     */
    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel(new MigLayout("insets 40, gap 15, flowy"));
        registerPanel.setBackground(new Color(0xFFFFFF));

        // Header
        JLabel createAccountLabel = new JLabel("Create Account");
        createAccountLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        createAccountLabel.setForeground(new Color(0x1E293B));
        registerPanel.add(createAccountLabel, "wrap, gapbottom 8");

        JLabel subtitleLabel = new JLabel("Join us today and start managing your inventory");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(0x64748B));
        registerPanel.add(subtitleLabel, "wrap, gapbottom 30");

        // Nama label and field
        JLabel namaLabel = new JLabel("Full Name");
        namaLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        namaLabel.setForeground(new Color(0x1E293B));
        registerPanel.add(namaLabel, "wrap, gapbottom 8");

        namaField = new RoundedTextField();
        namaField.setToolTipText("Enter your full name");
        registerPanel.add(namaField, "wrap, gapbottom 15, width 300px");

        // Username label and field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        usernameLabel.setForeground(new Color(0x1E293B));
        registerPanel.add(usernameLabel, "wrap, gapbottom 8");

        usernameField = new RoundedTextField();
        usernameField.setToolTipText("Choose a unique username");
        registerPanel.add(usernameField, "wrap, gapbottom 15, width 300px");

        // Password label and field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        passwordLabel.setForeground(new Color(0x1E293B));
        registerPanel.add(passwordLabel, "wrap, gapbottom 8");

        passwordField = new RoundedPasswordField();
        passwordField.setToolTipText("Create a strong password");
        registerPanel.add(passwordField, "wrap, gapbottom 15, width 300px");

        // Confirm Password label and field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        confirmPasswordLabel.setForeground(new Color(0x1E293B));
        registerPanel.add(confirmPasswordLabel, "wrap, gapbottom 8");

        confirmPasswordField = new RoundedPasswordField();
        confirmPasswordField.setToolTipText("Re-enter your password");
        registerPanel.add(confirmPasswordField, "wrap, gapbottom 15, width 300px");

        // Role label and field
        JLabel roleLabel = new JLabel("Role");
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        roleLabel.setForeground(new Color(0x1E293B));
        registerPanel.add(roleLabel, "wrap, gapbottom 8");

        String[] roles = {"Select a Role", "Administrator", "User", "Manager"};
        roleComboBox = new RoundedComboBox(roles);
        registerPanel.add(roleComboBox, "wrap, gapbottom 25, width 300px");

        // Button panel
        JPanel buttonPanel = new JPanel(new MigLayout("insets 0, gap 10"));
        buttonPanel.setBackground(new Color(0xFFFFFF));

        // Register button
        registerButton = new RoundedButton("Create Account", 20, 20);
        registerButton.setPreferredSize(new Dimension(180, 45));
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        buttonPanel.add(registerButton, "width 180px, height 45px");

        // Back button
        backButton = new RoundedButton("Back to Login", 20, 20);
        backButton.setPreferredSize(new Dimension(150, 45));
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        backButton.setButtonColor(new Color(0x64748B)); // Gray color
        buttonPanel.add(backButton, "width 150px, height 45px");

        registerPanel.add(buttonPanel, "wrap, gapbottom 20");

        // Login link
        JPanel loginPanel = new JPanel(new MigLayout("insets 0, gap 5"));
        loginPanel.setBackground(new Color(0xFFFFFF));

        JLabel hasAccountLabel = new JLabel("Already have an account?");
        hasAccountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        hasAccountLabel.setForeground(new Color(0x64748B));
        loginPanel.add(hasAccountLabel);

        JLabel loginLinkLabel = new JLabel("Login here");
        loginLinkLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        loginLinkLabel.setForeground(new Color(0x2563EB)); // Primary color
        loginLinkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginPanel.add(loginLinkLabel);

        registerPanel.add(loginPanel, "wrap");

        registerPanel.add(new JPanel(), "push");

        return registerPanel;
    }

    /**
     * Gets the nama field.
     */
    public JTextField getNamaField() {
        return namaField;
    }

    /**
     * Gets the username field.
     */
    public JTextField getUsernameField() {
        return usernameField;
    }

    /**
     * Gets the password field.
     */
    public JPasswordField getPasswordField() {
        return passwordField;
    }

    /**
     * Gets the confirm password field.
     */
    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    /**
     * Gets the role combo box.
     */
    public JComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    /**
     * Gets the register button.
     */
    public RoundedButton getRegisterButton() {
        return registerButton;
    }

    /**
     * Gets the back button.
     */
    public RoundedButton getBackButton() {
        return backButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterFrame frame = new RegisterFrame();
            frame.setVisible(true);
        });
    }
}
