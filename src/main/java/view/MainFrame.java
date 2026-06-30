package view;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;
import view.barang.BarangPanel;
import view.dashboard.DashboardPanel;
import view.kategori.KategoriPanel;
import view.transaksi.TransaksiPanel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    private static final String DASHBOARD_CARD = "dashboard";
    private static final String BARANG_CARD = "barang";
    private static final String KATEGORI_CARD = "kategori";
    private static final String TRANSAKSI_CARD = "transaksi";
    private static final Color SIDEBAR_BACKGROUND = new Color(15, 23, 42);
    private static final Color NAV_BACKGROUND = new Color(30, 41, 59);
    private static final Color NAV_HOVER_BACKGROUND = new Color(51, 65, 85);
    private static final Color CONTENT_BACKGROUND = new Color(248, 250, 252);

    private final CardLayout cardLayout;
    private final JPanel contentPanel;
    private final JLabel headerTitleLabel;
    private final DashboardPanel dashboardPanel;

    public MainFrame() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        headerTitleLabel = new JLabel("Dashboard");
        dashboardPanel = new DashboardPanel();

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
        mainPanel.setBackground(CONTENT_BACKGROUND);
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);

        add(sidebarPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebarPanel() {
        JPanel sidebarPanel = new JPanel(new MigLayout(
                "wrap 1,fillx,insets 28 18 28 18",
                "[200!,fill]",
                "[]36[]10[]10[]10[]push[]"
        ));
        sidebarPanel.setBackground(SIDEBAR_BACKGROUND);

        JLabel brandLabel = new JLabel("Inventaris Gudang");
        brandLabel.setIcon(FontIcon.of(FontAwesomeSolid.BOXES, 20, Color.WHITE));
        brandLabel.setIconTextGap(12);
        brandLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +3;foreground:#FFFFFF");

        sidebarPanel.add(brandLabel);
        sidebarPanel.add(createNavigationButton("Dashboard", DASHBOARD_CARD, FontAwesomeSolid.TACHOMETER_ALT));
        sidebarPanel.add(createNavigationButton("Barang", BARANG_CARD, FontAwesomeSolid.BOX));
        sidebarPanel.add(createNavigationButton("Kategori", KATEGORI_CARD, FontAwesomeSolid.TAGS));
        sidebarPanel.add(createNavigationButton("Transaksi", TRANSAKSI_CARD, FontAwesomeSolid.EXCHANGE_ALT));

        return sidebarPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new MigLayout(
                "fillx,insets 22 32 22 32",
                "[grow][]",
                "[]"
        ));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.putClientProperty(FlatClientProperties.STYLE, "border:0,0,1,0,$Component.borderColor");

        headerTitleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +8");
        JLabel userLabel = new JLabel("Admin");
        userLabel.setIcon(FontIcon.of(FontAwesomeSolid.USER_CIRCLE, 18, new Color(100, 116, 139)));
        userLabel.setIconTextGap(8);
        userLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        headerPanel.add(headerTitleLabel);
        headerPanel.add(userLabel);

        return headerPanel;
    }

    private JPanel createContentPanel() {
        contentPanel.add(dashboardPanel, DASHBOARD_CARD);
        contentPanel.add(new BarangPanel(), BARANG_CARD);
        contentPanel.add(new KategoriPanel(), KATEGORI_CARD);
        contentPanel.add(new TransaksiPanel(dashboardPanel::loadDashboardData), TRANSAKSI_CARD);

        return contentPanel;
    }

    private JButton createNavigationButton(String text, String cardName, FontAwesomeSolid icon) {
        JButton button = new JButton(text);
        button.setIcon(FontIcon.of(icon, 15, Color.WHITE));
        button.setIconTextGap(12);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:12;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "margin:11,16,11,16;"
                + "foreground:#FFFFFF;"
                + "background:#1E293B");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                button.setBackground(NAV_HOVER_BACKGROUND);
            }

            @Override
            public void mouseExited(MouseEvent event) {
                button.setBackground(NAV_BACKGROUND);
            }
        });
        button.addActionListener(event -> showPage(text, cardName));
        return button;
    }

    private void showPage(String title, String cardName) {
        headerTitleLabel.setText(title);
        if (DASHBOARD_CARD.equals(cardName)) {
            dashboardPanel.loadDashboardData();
        }
        cardLayout.show(contentPanel, cardName);
    }
}
