package com.inventarisgudang.view.dashboard;

import com.inventarisgudang.components.RoundedPanel;
import com.inventarisgudang.utils.TableStyleUtil;
import com.inventarisgudang.utils.UIConstants;
import net.miginfocom.swing.MigLayout;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Dashboard panel with statistics cards and latest transactions.
 * Displays key metrics and recent activity with modern ERP styling.
 */
public class DashboardPanel extends JPanel {
    private JTable transactionsTable;
    private DefaultTableModel tableModel;

    public DashboardPanel() {
        setLayout(new MigLayout("insets " + UIConstants.SPACING_2XL + ", gap " + UIConstants.SPACING_LG + ", flowy"));
        setBackground(UIConstants.CONTENT_BG);

        // Title
        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_LARGE_TITLE));
        titleLabel.setForeground(UIConstants.TEXT_PRIMARY);
        add(titleLabel, "wrap, gapbottom " + UIConstants.SPACING_XL);

        // Statistics cards panel
        JPanel cardsPanel = createStatisticsCards();
        add(cardsPanel, "growx, wrap, gapbottom " + UIConstants.SPACING_2XL);

        // Latest Transactions section
        JLabel transactionsLabel = new JLabel("Recent Transactions");
        transactionsLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_HEADING));
        transactionsLabel.setForeground(UIConstants.TEXT_PRIMARY);
        add(transactionsLabel, "wrap, gapbottom " + UIConstants.SPACING_LG);

        // Transactions table
        JPanel tablePanel = createTransactionsTable();
        add(tablePanel, "growx, grow, push, wrap");
    }

    /**
     * Creates statistics cards panel.
     */
    private JPanel createStatisticsCards() {
        JPanel cardsPanel = new JPanel(new MigLayout("insets 0, gap " + UIConstants.SPACING_LG));
        cardsPanel.setBackground(UIConstants.CONTENT_BG);

        // Total Barang Card
        JPanel totalBarangCard = createStatCard(
                "Total Barang",
                "156",
                UIConstants.PRIMARY,
                "📦"
        );
        cardsPanel.add(totalBarangCard, "growx, width 20%, height 140px");

        // Total Kategori Card
        JPanel totalKategoriCard = createStatCard(
                "Total Kategori",
                "12",
                UIConstants.SECONDARY,
                "🏷️"
        );
        cardsPanel.add(totalKategoriCard, "growx, width 20%, height 140px");

        // Barang Masuk Card
        JPanel barangMasukCard = createStatCard(
                "Barang Masuk",
                "1,234",
                UIConstants.ACCENT_GREEN,
                "📥"
        );
        cardsPanel.add(barangMasukCard, "growx, width 20%, height 140px");

        // Barang Keluar Card
        JPanel barangKeluarCard = createStatCard(
                "Barang Keluar",
                "892",
                UIConstants.ACCENT_ORANGE,
                "📤"
        );
        cardsPanel.add(barangKeluarCard, "growx, width 20%, height 140px, wrap");

        return cardsPanel;
    }

    /**
     * Creates a single statistics card with modern styling.
     */
    private JPanel createStatCard(String title, String value, Color accentColor, String icon) {
        RoundedPanel card = new RoundedPanel(UIConstants.CORNER_RADIUS_LARGE, UIConstants.CORNER_RADIUS_LARGE);
        card.setBackgroundColor(UIConstants.CARD_BG);
        card.setBorderColor(UIConstants.withAlpha(accentColor, 100));
        card.setBorderStroke(2.0f);
        card.setShadow(true);
        card.setLayout(new MigLayout("insets " + UIConstants.PADDING_COMFORTABLE + ", gap " + UIConstants.SPACING_MD + ", flowy"));

        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, UIConstants.FONT_XL + 8));
        card.add(iconLabel, "wrap");

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIConstants.createFont(UIConstants.FONT_BASE));
        titleLabel.setForeground(UIConstants.TEXT_SECONDARY);
        card.add(titleLabel, "wrap, gapbottom " + UIConstants.SPACING_MD);

        // Value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_LARGE_TITLE));
        valueLabel.setForeground(accentColor);
        card.add(valueLabel, "wrap");

        return card;
    }

    /**
     * Creates the transactions table.
     */
    private JPanel createTransactionsTable() {
        JPanel tablePanel = new JPanel(new MigLayout("insets 0, gap 0, flowy"));
        tablePanel.setBackground(UIConstants.CARD_BG);

        // Create table with dummy data
        String[] columnNames = {"ID", "Barang", "Tipe", "Jumlah", "Tanggal", "Keterangan"};
        Object[][] data = {
                {1001, "Laptop Dell XPS", "Masuk", 5, "2026-06-29", "Pembelian dari supplier A"},
                {1002, "Mouse Logitech", "Keluar", 25, "2026-06-29", "Penjualan ke toko retail"},
                {1003, "Keyboard Mechanical", "Masuk", 15, "2026-06-28", "Restok inventory"},
                {1004, "Monitor LG 27\"", "Keluar", 3, "2026-06-28", "Transfer ke gudang cabang"},
                {1005, "USB Hub", "Masuk", 50, "2026-06-27", "Pembelian dari supplier B"},
                {1006, "SSD Samsung 1TB", "Keluar", 8, "2026-06-27", "Penjualan korporat"},
                {1007, "RAM 16GB", "Masuk", 30, "2026-06-26", "Restok inventory"},
                {1008, "Webcam HD", "Keluar", 12, "2026-06-26", "Pengiriman ke klien utama"},
                {1009, "Headset Gaming", "Masuk", 20, "2026-06-25", "Pembelian dari supplier C"},
                {1010, "Cable HDMI", "Keluar", 100, "2026-06-25", "Penjualan retail bulk"}
        };

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        transactionsTable = new JTable(tableModel);
        
        // Apply modern table styling
        TableStyleUtil.styleTable(transactionsTable);

        // Column widths
        transactionsTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        transactionsTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        transactionsTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        transactionsTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        transactionsTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        transactionsTable.getColumnModel().getColumn(5).setPreferredWidth(250);

        // Scroll pane with rounded corners appearance
        JScrollPane scrollPane = new JScrollPane(transactionsTable);
        scrollPane.setBackground(UIConstants.CARD_BG);
        scrollPane.getViewport().setBackground(UIConstants.CARD_BG);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT, 1));

        tablePanel.add(scrollPane, "growx, grow, push");

        return tablePanel;
    }

    /**
     * Gets the transactions table.
     */
    public JTable getTransactionsTable() {
        return transactionsTable;
    }

    /**
     * Gets the table model for data manipulation.
     */
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    /**
     * Adds a new transaction row to the table.
     */
    public void addTransaction(Object[] rowData) {
        tableModel.insertRow(0, rowData);
    }

    /**
     * Clears all transaction rows.
     */
    public void clearTransactions() {
        tableModel.setRowCount(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dashboard Panel Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 700);
            frame.setLocationRelativeTo(null);
            frame.add(new DashboardPanel());
            frame.setVisible(true);
        });
    }
}
