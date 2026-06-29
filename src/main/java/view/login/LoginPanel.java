package view.login;

import com.formdev.flatlaf.FlatClientProperties;
import dao.UserDAO;
import model.User;
import net.miginfocom.swing.MigLayout;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.sql.SQLException;
import java.util.List;

public class LoginPanel extends JPanel {
    private final UserDAO userDAO;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JCheckBox rememberMeCheckBox;
    private final JButton loginButton;

    public LoginPanel() {
        userDAO = new UserDAO();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        rememberMeCheckBox = new JCheckBox("Ingat saya");
        loginButton = new JButton("Masuk");

        initializeLayout();
        initializeActions();
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

        JPanel loginCard = new JPanel(new MigLayout(
                "wrap 1,fillx,insets 36 40 36 40",
                "[360!,fill]",
                "[]18[]8[]16[]8[]12[]22[]"
        ));
        loginCard.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:16;"
                + "background:$Panel.background;"
                + "border:1,1,1,1,$Component.borderColor");

        JLabel titleLabel = new JLabel("Inventaris Gudang");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        JLabel subtitleLabel = new JLabel("Masuk untuk mengelola stok gudang");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(usernameLabel.getFont().deriveFont(Font.BOLD));
        usernameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan username");

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(passwordLabel.getFont().deriveFont(Font.BOLD));
        passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan password");

        loginButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:10;"
                + "font:bold +1;"
                + "margin:8,12,8,12");

        loginCard.add(titleLabel);
        loginCard.add(subtitleLabel);
        loginCard.add(usernameLabel);
        loginCard.add(usernameField);
        loginCard.add(passwordLabel);
        loginCard.add(passwordField);
        loginCard.add(rememberMeCheckBox);
        loginCard.add(loginButton);

        contentPanel.add(loginCard, "align center");
        add(contentPanel, BorderLayout.CENTER);
    }

    private void initializeActions() {
        loginButton.addActionListener(event -> login());
        passwordField.addActionListener(event -> login());
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Username dan password wajib diisi.",
                    "Login Gagal",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            User user = authenticate(username, password);

            if (user == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Username atau password salah.",
                        "Login Gagal",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            openMainFrame();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tidak dapat terhubung ke database.",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (ReflectiveOperationException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "MainFrame belum tersedia.",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private User authenticate(String username, String password) throws SQLException {
        List<User> users = userDAO.findAll();

        for (User user : users) {
            if (username.equals(user.getUsername()) && isPasswordValid(password, user.getPassword())) {
                return user;
            }
        }

        return null;
    }

    private boolean isPasswordValid(String password, String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isBlank()) {
            return false;
        }

        try {
            return BCrypt.checkpw(password, hashedPassword);
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    private void openMainFrame() throws ReflectiveOperationException {
        Object frame = Class.forName("view.MainFrame").getDeclaredConstructor().newInstance();

        if (!(frame instanceof JFrame mainFrame)) {
            throw new ReflectiveOperationException("view.MainFrame must extend JFrame");
        }

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        Window loginWindow = SwingUtilities.getWindowAncestor(this);
        if (loginWindow != null) {
            loginWindow.dispose();
        }
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JCheckBox getRememberMeCheckBox() {
        return rememberMeCheckBox;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
