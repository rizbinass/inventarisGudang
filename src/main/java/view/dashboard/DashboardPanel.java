package view.dashboard;

import com.formdev.flatlaf.FlatClientProperties;
import config.DatabaseConnection;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class DashboardPanel extends JPanel {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    private static final Color CARD_HOVER_COLOR = new Color(241, 245, 249);

    private final JLabel totalBarangValueLabel;
    private final JLabel totalKategoriValueLabel;
    private final JLabel barangMasukValueLabel;
    private final JLabel barangKeluarValueLabel;
    private final JTable recentTransactionsTable;
    private final DefaultTableModel tableModel;

    public DashboardPanel() {
        totalBarangValueLabel = new JLabel("0");
        totalKategoriValueLabel = new JLabel("0");
        barangMasukValueLabel = new JLabel("0");
        barangKeluarValueLabel = new JLabel("0");
        recentTransactionsTable = new JTable();
        tableModel = createTableModel();

        initializeLayout();
        initializeTable();
        loadDashboardData();
    }

    private void initializeLayout() {
        setLayout(new MigLayout(
                "fill,insets 32",
                "[grow][grow][grow][grow]",
                "[]24[grow]"
        ));
        setBackground(BACKGROUND_COLOR);

        add(createSummaryCard("Total Barang", totalBarangValueLabel, "Item tersedia", FontAwesomeSolid.BOX), "growx");
        add(createSummaryCard("Total Kategori", totalKategoriValueLabel, "Jenis barang", FontAwesomeSolid.TAGS), "growx");
        add(createSummaryCard("Barang Masuk", barangMasukValueLabel, "Total transaksi masuk", FontAwesomeSolid.PLUS_CIRCLE), "growx");
        add(createSummaryCard("Barang Keluar", barangKeluarValueLabel, "Total transaksi keluar", FontAwesomeSolid.EXCHANGE_ALT), "growx,wrap");
        add(createRecentTransactionsPanel(), "span,grow");
    }

    private JPanel createSummaryCard(String title, JLabel valueLabel, String description, FontAwesomeSolid icon) {
        JPanel card = new JPanel(new MigLayout(
                "fillx,insets 22",
                "[grow][]",
                "[]8[]8[]"
        ));
        card.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:18;"
                + "background:$Panel.background;"
                + "border:1,1,1,1,$Component.borderColor");
        addCardHover(card);

        JLabel titleLabel = new JLabel(title);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");
        valueLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        JLabel iconLabel = new JLabel(FontIcon.of(icon, 22, new Color(37, 99, 235)));
        iconLabel.putClientProperty(FlatClientProperties.STYLE, "arc:14;background:#EFF6FF;border:10,10,10,10,#EFF6FF");

        card.add(titleLabel, "cell 0 0");
        card.add(iconLabel, "cell 1 0 1 2,align center top");
        card.add(valueLabel, "cell 0 1");
        card.add(descriptionLabel, "cell 0 2");

        return card;
    }

    private JPanel createRecentTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:18;"
                + "background:$Panel.background;"
                + "border:1,1,1,1,$Component.borderColor");
        addCardHover(panel);

        JLabel titleLabel = new JLabel("Transaksi Terbaru");
        titleLabel.setIcon(FontIcon.of(FontAwesomeSolid.EXCHANGE_ALT, 16, new Color(37, 99, 235)));
        titleLabel.setIconTextGap(10);
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 0, 20));
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18F));

        JScrollPane scrollPane = new JScrollPane(recentTransactionsTable);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 20, 20));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void initializeTable() {
        recentTransactionsTable.setModel(tableModel);
        recentTransactionsTable.setRowHeight(36);
        recentTransactionsTable.setShowGrid(false);
        recentTransactionsTable.getTableHeader().setReorderingAllowed(false);
    }

    private void addCardHover(JPanel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                panel.setBackground(CARD_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent event) {
                panel.setBackground(null);
            }
        });
    }

    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(
                new String[]{"ID", "Barang", "Jenis", "Jumlah", "Tanggal"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public void loadDashboardData() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            totalBarangValueLabel.setText(String.valueOf(countBarang(connection)));
            totalKategoriValueLabel.setText(String.valueOf(countKategori(connection)));
            barangMasukValueLabel.setText(String.valueOf(sumTransaksiJumlah(connection, "Masuk")));
            barangKeluarValueLabel.setText(String.valueOf(sumTransaksiJumlah(connection, "Keluar")));
            loadRecentTransactions(connection);
        } catch (SQLException exception) {
            showErrorMessage("Gagal memuat data dashboard.");
        }
    }

    private int countBarang(Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM barang";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        }

        return 0;
    }

    private int countKategori(Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM kategori";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        }

        return 0;
    }

    private int sumTransaksiJumlah(Connection connection, String jenisTransaksi) throws SQLException {
        String sql = "SELECT COALESCE(SUM(jumlah), 0) AS total FROM transaksi WHERE jenis_transaksi = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, jenisTransaksi);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("total");
                }
            }
        }

        return 0;
    }

    private void loadRecentTransactions(Connection connection) throws SQLException {
        String sql = """
                SELECT t.id, b.nama_barang, t.jenis_transaksi, t.jumlah, t.tanggal_transaksi
                FROM transaksi t
                JOIN barang b ON t.barang_id = b.id
                ORDER BY t.tanggal_transaksi DESC, t.id DESC
                LIMIT 10
                """;

        tableModel.setRowCount(0);

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getInt("id"),
                        resultSet.getString("nama_barang"),
                        resultSet.getString("jenis_transaksi"),
                        resultSet.getInt("jumlah"),
                        resultSet.getTimestamp("tanggal_transaksi").toLocalDateTime().format(DATE_TIME_FORMATTER)
                });
            }
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
