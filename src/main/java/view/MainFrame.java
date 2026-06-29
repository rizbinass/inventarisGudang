package view;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

public class MainFrame extends JFrame {
    private static final String DASHBOARD_CARD = "dashboard";
    private static final String BARANG_CARD = "barang";
    private static final String KATEGORI_CARD = "kategori";
    private static final String TRANSAKSI_CARD = "transaksi";

    private final CardLayout cardLayout;
    private final JPanel contentPanel;
    private final JLabel headerTitleLabel;

    public MainFrame() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        headerTitleLabel = new JLabel("Dashboard");

        initializeFrame();
        initializeLayout();
    }

    private void initializeFrame() {
        setTitle("Inventaris Gudang");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 680));
        setSize(1180, 760);
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());

        JPanel sidebarPanel = createSidebarPanel();
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);

        add(sidebarPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebarPanel() {
        JPanel sidebarPanel = new JPanel(new MigLayout(
                "wrap 1,fillx,insets 24 16 24 16",
                "[200!,fill]",
                "[]32[]8[]8[]8[]push[]"
        ));
        sidebarPanel.setBackground(new Color(30, 41, 59));

        JLabel brandLabel = new JLabel("Inventaris Gudang");
        brandLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +3;foreground:#FFFFFF");

        sidebarPanel.add(brandLabel);
        sidebarPanel.add(createNavigationButton("Dashboard", DASHBOARD_CARD));
        sidebarPanel.add(createNavigationButton("Barang", BARANG_CARD));
        sidebarPanel.add(createNavigationButton("Kategori", KATEGORI_CARD));
        sidebarPanel.add(createNavigationButton("Transaksi", TRANSAKSI_CARD));

        return sidebarPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new MigLayout(
                "fillx,insets 20 28 20 28",
                "[grow][]",
                "[]"
        ));

        headerTitleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +8");
        JLabel userLabel = new JLabel("Admin");
        userLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        headerPanel.add(headerTitleLabel);
        headerPanel.add(userLabel);

        return headerPanel;
    }

    private JPanel createContentPanel() {
        contentPanel.add(createPlaceholderPanel("Dashboard"), DASHBOARD_CARD);
        contentPanel.add(createPlaceholderPanel("Barang"), BARANG_CARD);
        contentPanel.add(createPlaceholderPanel("Kategori"), KATEGORI_CARD);
        contentPanel.add(createPlaceholderPanel("Transaksi"), TRANSAKSI_CARD);

        return contentPanel;
    }

    private JButton createNavigationButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:10;"
                + "margin:10,14,10,14;"
                + "foreground:#FFFFFF;"
                + "background:#334155");
        button.addActionListener(event -> showPage(text, cardName));
        return button;
    }

    private JPanel createPlaceholderPanel(String title) {
        JPanel panel = new JPanel(new MigLayout(
                "fill,insets 28",
                "[grow]",
                "[grow]"
        ));
        panel.setBackground(new Color(245, 247, 250));

        JLabel label = new JLabel(title);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.putClientProperty(FlatClientProperties.STYLE, "font:bold +12;foreground:$Label.disabledForeground");

        panel.add(label, "align center");
        return panel;
    }

    private void showPage(String title, String cardName) {
        headerTitleLabel.setText(title);
        cardLayout.show(contentPanel, cardName);
    }
}
