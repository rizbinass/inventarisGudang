package view.dashboard;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

public class DashboardPanel extends JPanel {
    private final JTable recentTransactionsTable;

    public DashboardPanel() {
        recentTransactionsTable = new JTable();

        initializeLayout();
        initializeTable();
    }

    private void initializeLayout() {
        setLayout(new MigLayout(
                "fill,insets 28",
                "[grow][grow][grow][grow]",
                "[][grow]"
        ));
        setBackground(new Color(245, 247, 250));

        add(createSummaryCard("Total Barang", "128", "Item tersedia"), "growx");
        add(createSummaryCard("Kategori", "12", "Jenis barang"), "growx");
        add(createSummaryCard("Barang Masuk", "34", "Transaksi bulan ini"), "growx");
        add(createSummaryCard("Barang Keluar", "21", "Transaksi bulan ini"), "growx,wrap");
        add(createRecentTransactionsPanel(), "span,grow");
    }

    private JPanel createSummaryCard(String title, String value, String description) {
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

        JLabel valueLabel = new JLabel(value);
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
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][]{
                        {"TRX-001", "Keyboard Mechanical", "Masuk", 10, "2026-06-30"},
                        {"TRX-002", "Mouse Wireless", "Keluar", 4, "2026-06-30"},
                        {"TRX-003", "Monitor 24 Inch", "Masuk", 6, "2026-06-29"},
                        {"TRX-004", "Kabel HDMI", "Keluar", 12, "2026-06-29"},
                        {"TRX-005", "Printer Laser", "Masuk", 2, "2026-06-28"}
                },
                new String[]{"Kode", "Barang", "Jenis", "Jumlah", "Tanggal"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        recentTransactionsTable.setModel(tableModel);
        recentTransactionsTable.setRowHeight(36);
        recentTransactionsTable.setShowGrid(false);
        recentTransactionsTable.getTableHeader().setReorderingAllowed(false);
    }
}
