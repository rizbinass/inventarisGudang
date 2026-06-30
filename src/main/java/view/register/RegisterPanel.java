package view.register;

import com.formdev.flatlaf.FlatClientProperties;
import dao.UserDAO;
import model.User;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class RegisterPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    private static final Color BUTTON_HOVER_COLOR = new Color(29, 78, 216);

    private final JTextField nameField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JButton registerButton;
    private final UserDAO userDAO;

    public RegisterPanel() {
        userDAO = new UserDAO();
        nameField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        registerButton = new JButton("Daftar");

        initializeLayout();
        initializeActions();
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        JPanel contentPanel = new JPanel(new MigLayout(
                "fill,insets 0",
                "[grow]",
                "[grow]"
        ));
        contentPanel.setOpaque(false);

        JPanel registerCard = new JPanel(new MigLayout(
                "wrap 1,fillx,insets 40 44 40 44",
                "[360!,fill]",
                "[]18[]8[]14[]8[]14[]8[]14[]8[]22[]"
        ));
        registerCard.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "background:$Panel.background;"
                + "border:1,1,1,1,$Component.borderColor");

        JLabel titleLabel = new JLabel("Buat Akun");
        titleLabel.setIcon(FontIcon.of(FontAwesomeSolid.USER_PLUS, 24, new Color(37, 99, 235)));
        titleLabel.setIconTextGap(12);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        JLabel subtitleLabel = new JLabel("Daftar untuk mengakses inventaris gudang");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        JLabel nameLabel = createFieldLabel("Nama");
        nameLabel.setIcon(FontIcon.of(FontAwesomeSolid.USER, 13, new Color(100, 116, 139)));
        nameLabel.setIconTextGap(8);
        nameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan nama lengkap");
        nameField.putClientProperty(FlatClientProperties.STYLE, "arc:12;margin:8,12,8,12");

        JLabel usernameLabel = createFieldLabel("Username");
        usernameLabel.setIcon(FontIcon.of(FontAwesomeSolid.USER_CIRCLE, 13, new Color(100, 116, 139)));
        usernameLabel.setIconTextGap(8);
        usernameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan username");
        usernameField.putClientProperty(FlatClientProperties.STYLE, "arc:12;margin:8,12,8,12");

        JLabel passwordLabel = createFieldLabel("Password");
        passwordLabel.setIcon(FontIcon.of(FontAwesomeSolid.LOCK, 13, new Color(100, 116, 139)));
        passwordLabel.setIconTextGap(8);
        passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan password");
        passwordField.putClientProperty(FlatClientProperties.STYLE, "arc:12;margin:8,12,8,12");

        JLabel confirmPasswordLabel = createFieldLabel("Konfirmasi Password");
        confirmPasswordLabel.setIcon(FontIcon.of(FontAwesomeSolid.LOCK, 13, new Color(100, 116, 139)));
        confirmPasswordLabel.setIconTextGap(8);
        confirmPasswordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ulangi password");
        confirmPasswordField.putClientProperty(FlatClientProperties.STYLE, "arc:12;margin:8,12,8,12");

        registerButton.setIcon(FontIcon.of(FontAwesomeSolid.USER_PLUS, 14));
        registerButton.setIconTextGap(8);
        registerButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:12;"
                + "font:bold +1;"
                + "margin:10,14,10,14");
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                registerButton.setBackground(BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent event) {
                registerButton.setBackground(null);
            }
        });

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

    private void initializeActions() {
        registerButton.addActionListener(event -> register());
        confirmPasswordField.addActionListener(event -> register());
    }

    private void register() {
        String nama = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (nama.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showWarningMessage("Semua field wajib diisi.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showWarningMessage("Konfirmasi password tidak sesuai.");
            return;
        }

        try {
            if (userDAO.findByUsername(username) != null) {
                showWarningMessage("Username sudah digunakan.");
                return;
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            userDAO.create(new User(0, username, hashedPassword, nama));

            JOptionPane.showMessageDialog(
                    this,
                    "Registrasi berhasil.",
                    "Register",
                    JOptionPane.INFORMATION_MESSAGE
            );
            clearForm();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Registrasi gagal. Tidak dapat menyimpan user.",
                    "Register Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearForm() {
        nameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    private void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
        );
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
