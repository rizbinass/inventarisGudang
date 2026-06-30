package view.login;

import com.formdev.flatlaf.FlatClientProperties;
import dao.UserDAO;
import model.User;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class LoginPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    private static final Color BUTTON_HOVER_COLOR = new Color(29, 78, 216);

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
        setBackground(BACKGROUND_COLOR);

        JPanel contentPanel = new JPanel(new MigLayout(
                "fill,insets 0",
                "[grow]",
                "[grow]"
        ));
        contentPanel.setOpaque(false);

        JPanel loginCard = new JPanel(new MigLayout(
                "wrap 1,fillx,insets 40 44 40 44",
                "[360!,fill]",
                "[]18[]8[]16[]8[]12[]22[]"
        ));
        loginCard.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "background:$Panel.background;"
                + "border:1,1,1,1,$Component.borderColor");

        JLabel titleLabel = new JLabel("Inventaris Gudang");
        titleLabel.setIcon(FontIcon.of(FontAwesomeSolid.BOXES, 24, new Color(37, 99, 235)));
        titleLabel.setIconTextGap(12);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        JLabel subtitleLabel = new JLabel("Masuk untuk mengelola stok gudang");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setIcon(FontIcon.of(FontAwesomeSolid.USER, 13, new Color(100, 116, 139)));
        usernameLabel.setIconTextGap(8);
        usernameLabel.setFont(usernameLabel.getFont().deriveFont(Font.BOLD));
        usernameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan username");
        usernameField.putClientProperty(FlatClientProperties.STYLE, "arc:12;margin:8,12,8,12");

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setIcon(FontIcon.of(FontAwesomeSolid.LOCK, 13, new Color(100, 116, 139)));
        passwordLabel.setIconTextGap(8);
        passwordLabel.setFont(passwordLabel.getFont().deriveFont(Font.BOLD));
        passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan password");
        passwordField.putClientProperty(FlatClientProperties.STYLE, "arc:12;margin:8,12,8,12");

        loginButton.setIcon(FontIcon.of(FontAwesomeSolid.SIGN_IN_ALT, 14));
        loginButton.setIconTextGap(8);
        loginButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:12;"
                + "font:bold +1;"
                + "margin:10,14,10,14");
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                loginButton.setBackground(BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent event) {
                loginButton.setBackground(null);
            }
        });

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
        User user = userDAO.findByUsername(username);

        if (user != null && isPasswordValid(password, user.getPassword())) {
            return user;
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
