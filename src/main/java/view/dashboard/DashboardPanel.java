package view.dashboard;

import com.formdev.flatlaf.FlatClientProperties;
import config.DatabaseConnection;
import net.miginfocom.swing.MigLayout;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class DashboardPanel extends JPanel {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
                "fill,insets 28",
                "[grow][grow][grow][grow]",
                "[][grow]"
        ));
        setBackground(new Color(245, 247, 250));

        add(createSummaryCard("Total Barang", totalBarangValueLabel, "Item tersedia"), "growx");
        add(createSummaryCard("Total Kategori", totalKategoriValueLabel, "Jenis barang"), "growx");
        add(createSummaryCard("Barang Masuk", barangMasukValueLabel, "Total transaksi masuk"), "growx");
        add(createSummaryCard("Barang Keluar", barangKeluarValueLabel, "Total transaksi keluar"), "growx,wrap");
        add(createRecentTransactionsPanel(), "span,grow");
    }

    private JPanel createSummaryCard(String title, JLabel valueLabel, String description) {
        JPanel card = new JPanel(new MigLayout(
                "wrap 1,fillx,insets 20",
                "[grow]",
                "[]8[]8[]"
        ));
        card.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:12;"
                + "background:$Panel.background;"
                + "border:1,1,1,1,$Component.borderColor");

        JLabel titleLabel = new JLabel(title);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");
        valueLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        card.add(titleLabel);
        card.add(valueLabel);
        card.add(descriptionLabel);

        return card;
    }

    private JPanel createRecentTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:12;"
                + "background:$Panel.background;"
                + "border:1,1,1,1,$Component.borderColor");

        JLabel titleLabel = new JLabel("Transaksi Terbaru");
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
            totalBarangValueLabel.setText(String.valueOf(countRows(connection, "barang")));
            totalKategoriValueLabel.setText(String.valueOf(countRows(connection, "kategori")));
            barangMasukValueLabel.setText(String.valueOf(sumTransaksiJumlah(connection, "Masuk")));
            barangKeluarValueLabel.setText(String.valueOf(sumTransaksiJumlah(connection, "Keluar")));
            loadRecentTransactions(connection);
        } catch (SQLException exception) {
            showErrorMessage("Gagal memuat data dashboard.");
        }
    }

    private int countRows(Connection connection, String tableName) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM " + tableName;

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
