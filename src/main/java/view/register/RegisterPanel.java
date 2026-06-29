package view.register;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

public class RegisterPanel extends JPanel {
    private final JTextField nameField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JButton registerButton;

    public RegisterPanel() {
        nameField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        registerButton = new JButton("Daftar");

        initializeLayout();
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JPanel contentPanel = new JPanel(new MigLayout(
                "fill,insets 0",
                "[grow]",
                "[grow]"
        ));
        contentPanel.setOpaque(false);

        JPanel registerCard = new JPanel(new MigLayout(
                "wrap 1,fillx,insets 36 40 36 40",
                "[360!,fill]",
                "[]18[]8[]14[]8[]14[]8[]14[]8[]22[]"
        ));
        registerCard.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:16;"
                + "background:$Panel.background;"
                + "border:1,1,1,1,$Component.borderColor");

        JLabel titleLabel = new JLabel("Buat Akun");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        JLabel subtitleLabel = new JLabel("Daftar untuk mengakses inventaris gudang");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        JLabel nameLabel = createFieldLabel("Nama");
        nameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan nama lengkap");

        JLabel usernameLabel = createFieldLabel("Username");
        usernameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan username");

        JLabel passwordLabel = createFieldLabel("Password");
        passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan password");

        JLabel confirmPasswordLabel = createFieldLabel("Konfirmasi Password");
        confirmPasswordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ulangi password");

        registerButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:10;"
                + "font:bold +1;"
                + "margin:8,12,8,12");

        registerCard.add(titleLabel);
        registerCard.add(subtitleLabel);
        registerCard.add(nameLabel);
        registerCard.add(nameField);
        registerCard.add(usernameLabel);
        registerCard.add(usernameField);
        registerCard.add(passwordLabel);
        registerCard.add(passwordField);
        registerCard.add(confirmPasswordLabel);
        registerCard.add(confirmPasswordField);
        registerCard.add(registerButton);

        contentPanel.add(registerCard, "align center");
        add(contentPanel, BorderLayout.CENTER);
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        return label;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }
}
