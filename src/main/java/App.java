import com.formdev.flatlaf.FlatLightLaf;

import config.DatabaseInitializer;
import view.login.LoginPanel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Dimension;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::start);
    }

    private static void start() {
        FlatLightLaf.setup();
        UIManager.put("Button.arc", 10);
        UIManager.put("Component.arc", 10);
        UIManager.put("TextComponent.arc", 10);

        try {
            DatabaseInitializer.initialize();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(
                    null,
                    "Database gagal disiapkan. Pastikan MySQL aktif dan konfigurasi benar.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JFrame frame = new JFrame("Inventaris Gudang");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setSize(1024, 680);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new LoginPanel());
        frame.setVisible(true);
    }
}
