package com.inventarisgudang.view.transaksi;

import com.inventarisgudang.components.RoundedButton;
import com.inventarisgudang.components.RoundedTextField;
import com.inventarisgudang.utils.UIConstants;
import net.miginfocom.swing.MigLayout;


import javax.swing.*;
import javax.swing.table.JTableHeader;

import javax.swing.table.DefaultTableModel;
import java.awt.*;


/**
 * Panel for managing Transaksi (Transactions).
 * Displays incoming and outgoing items with transaction history.
 */
public class TransaksiPanel extends JPanel {
    private JTabbedPane tabbedPane;
    private JPanel barangMasukPanel;
    private JPanel barangKeluarPanel;
    private JPanel historyPanel;
    private RoundedButton addMasukButton;
    private RoundedButton addKeluarButton;
    private RoundedTextField searchField;
    private RoundedButton refreshButton;
    private JTable historyTable;
    private DefaultTableModel historyTableModel;

    public TransaksiPanel() {
        setLayout(new MigLayout("insets 30, gap " + UIConstants.SPACING_LG + ", flowy"));


        setBackground(UIConstants.CONTENT_BG);


        // Title
        JLabel titleLabel = new JLabel("Manajemen Transaksi");
        titleLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_HERO));
        titleLabel.setForeground(UIConstants.TEXT_PRIMARY);

        add(titleLabel, "wrap, gapbottom 20");

        // Tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(UIConstants.createBoldFont(UIConstants.FONT_BASE));
        tabbedPane.setBackground(UIConstants.CARD_BG);
        tabbedPane.setForeground(UIConstants.TEXT_PRIMARY);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);


        // Barang Masuk tab
        barangMasukPanel = createBarangMasukPanel();
        tabbedPane.addTab("📥 Barang Masuk", barangMasukPanel);

        // Barang Keluar tab
        barangKeluarPanel = createBarangKeluarPanel();
        tabbedPane.addTab("📤 Barang Keluar", barangKeluarPanel);

        // History tab
        historyPanel = createHistoryPanel();
        tabbedPane.addTab("📊 History", historyPanel);

        add(tabbedPane, "growx, grow, push, wrap");
    }

    /**
     * Creates the Barang Masuk panel.
     */
    private JPanel createBarangMasukPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 20, gap " + UIConstants.SPACING_LG + ", flowy"));
        panel.setBackground(UIConstants.CONTENT_BG);


        // Title
        JLabel titleLabel = new JLabel("Barang Masuk");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0x1E293B));
        panel.add(titleLabel, "wrap, gapbottom 15");

        // Toolbar
        JPanel toolbar = new JPanel(new MigLayout("insets 0, gap 15"));
        toolbar.setBackground(new Color(0xF8FAFC));

        JLabel searchLabel = new JLabel("Cari:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        searchLabel.setForeground(new Color(0x1E293B));
        toolbar.add(searchLabel);

        RoundedTextField searchFieldMasuk = new RoundedTextField();
        searchFieldMasuk.setToolTipText("Cari barang masuk");
        toolbar.add(searchFieldMasuk, "width 250px");

        toolbar.add(new JPanel(), "push");

        addMasukButton = new RoundedButton("+ Tambah Masuk", 20, 20);
        addMasukButton.setPreferredSize(new Dimension(130, 38));
        addMasukButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        toolbar.add(addMasukButton);

        RoundedButton refreshMasukButton = new RoundedButton("🔄 Refresh", 20, 20);
        refreshMasukButton.setPreferredSize(new Dimension(100, 38));
        refreshMasukButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        refreshMasukButton.setButtonColor(new Color(0x64748B));
        toolbar.add(refreshMasukButton);

        panel.add(toolbar, "growx, wrap, gapbottom 15");

        // Barang Masuk Table
        JPanel tablePanel = createBarangMasukTable();
        panel.add(tablePanel, "growx, grow, push, wrap");

        return panel;
    }

    /**
     * Creates the Barang Masuk table.
     */
    private JPanel createBarangMasukTable() {
        JPanel tablePanel = new JPanel(new MigLayout("insets 0, gap 0, flowy"));
        tablePanel.setBackground(new Color(0xFFFFFF));

        String[] columnNames = {"ID", "Tanggal", "Barang", "Supplier", "Jumlah", "Satuan", "Harga Satuan", "Total", "Keterangan"};
        Object[][] data = {
                {1, "2026-06-29", "Laptop Dell XPS", "Supplier A", 5, "Unit", 12000000, 60000000, "Pembelian reguler"},
                {2, "2026-06-28", "Mouse Logitech", "Supplier B", 25, "Unit", 150000, 3750000, "Restok"},
                {3, "2026-06-27", "USB Hub", "Supplier A", 50, "Unit", 100000, 5000000, "Pembelian bulk"},
                {4, "2026-06-26", "RAM 16GB", "Supplier C", 30, "Unit", 400000, 12000000, "Upgrade stok"},
                {5, "2026-06-25", "Keyboard Mechanical", "Supplier B", 15, "Unit", 500000, 7500000, "Restok"},
        };

        DefaultTableModel masukTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable masukTable = new JTable(masukTableModel);
        masukTable.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        masukTable.setRowHeight(28);
        masukTable.setBackground(new Color(0xFFFFFF));
        masukTable.setForeground(new Color(0x1E293B));
        masukTable.setGridColor(new Color(0xE2E8F0));
        masukTable.setSelectionBackground(new Color(0xE0E7FF));
        masukTable.setSelectionForeground(new Color(0x1E293B));

        int[] columnWidths = {50, 80, 130, 110, 70, 60, 110, 110, 150};
        for (int i = 0; i < columnWidths.length; i++) {
            masukTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JTableHeader header = masukTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(new Color(0xF1F5F9));
        header.setForeground(new Color(0x1E293B));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xE2E8F0)));

        JScrollPane scrollPane = new JScrollPane(masukTable);
        scrollPane.setBackground(new Color(0xFFFFFF));
        scrollPane.getViewport().setBackground(new Color(0xFFFFFF));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0xE2E8F0), 1));

        tablePanel.add(scrollPane, "growx, grow, push");

        return tablePanel;
    }

    /**
     * Creates the Barang Keluar panel.
     */
    private JPanel createBarangKeluarPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 20, gap 20, flowy"));
        panel.setBackground(new Color(0xF8FAFC));

        // Title
        JLabel titleLabel = new JLabel("Barang Keluar");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0x1E293B));
        panel.add(titleLabel, "wrap, gapbottom 15");

        // Toolbar
        JPanel toolbar = new JPanel(new MigLayout("insets 0, gap 15"));
        toolbar.setBackground(new Color(0xF8FAFC));

        JLabel searchLabel = new JLabel("Cari:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        searchLabel.setForeground(new Color(0x1E293B));
        toolbar.add(searchLabel);

        RoundedTextField searchFieldKeluar = new RoundedTextField();
        searchFieldKeluar.setToolTipText("Cari barang keluar");
        toolbar.add(searchFieldKeluar, "width 250px");

        toolbar.add(new JPanel(), "push");

        addKeluarButton = new RoundedButton("+ Tambah Keluar", 20, 20);
        addKeluarButton.setPreferredSize(new Dimension(130, 38));
        addKeluarButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        toolbar.add(addKeluarButton);

        RoundedButton refreshKeluarButton = new RoundedButton("🔄 Refresh", 20, 20);
        refreshKeluarButton.setPreferredSize(new Dimension(100, 38));
        refreshKeluarButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        refreshKeluarButton.setButtonColor(new Color(0x64748B));
        toolbar.add(refreshKeluarButton);

        panel.add(toolbar, "growx, wrap, gapbottom 15");

        // Barang Keluar Table
        JPanel tablePanel = createBarangKeluarTable();
        panel.add(tablePanel, "growx, grow, push, wrap");

        return panel;
    }

    /**
     * Creates the Barang Keluar table.
     */
    private JPanel createBarangKeluarTable() {
        JPanel tablePanel = new JPanel(new MigLayout("insets 0, gap 0, flowy"));
        tablePanel.setBackground(new Color(0xFFFFFF));

        String[] columnNames = {"ID", "Tanggal", "Barang", "Tujuan", "Jumlah", "Satuan", "Harga Satuan", "Total", "Keterangan"};
        Object[][] data = {
                {1, "2026-06-29", "Mouse Logitech", "Toko Retail A", 25, "Unit", 250000, 6250000, "Penjualan"},
                {2, "2026-06-28", "Monitor LG 27\"", "Toko Retail B", 3, "Unit", 3500000, 10500000, "Transfer gudang"},
                {3, "2026-06-27", "Cable HDMI", "Retailer Online", 100, "Unit", 50000, 5000000, "Penjualan bulk"},
                {4, "2026-06-26", "SSD Samsung 1TB", "Klien Korporat", 8, "Unit", 1200000, 9600000, "Penjualan B2B"},
                {5, "2026-06-25", "Headset Gaming", "Klien Retail", 12, "Unit", 700000, 8400000, "Penjualan"},
        };

        DefaultTableModel keluarTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable keluarTable = new JTable(keluarTableModel);
        keluarTable.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        keluarTable.setRowHeight(28);
        keluarTable.setBackground(new Color(0xFFFFFF));
        keluarTable.setForeground(new Color(0x1E293B));
        keluarTable.setGridColor(new Color(0xE2E8F0));
        keluarTable.setSelectionBackground(new Color(0xE0E7FF));
        keluarTable.setSelectionForeground(new Color(0x1E293B));

        int[] columnWidths = {50, 80, 130, 130, 70, 60, 110, 110, 150};
        for (int i = 0; i < columnWidths.length; i++) {
            keluarTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JTableHeader header = keluarTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(new Color(0xF1F5F9));
        header.setForeground(new Color(0x1E293B));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xE2E8F0)));

        JScrollPane scrollPane = new JScrollPane(keluarTable);
        scrollPane.setBackground(new Color(0xFFFFFF));
        scrollPane.getViewport().setBackground(new Color(0xFFFFFF));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0xE2E8F0), 1));

        tablePanel.add(scrollPane, "growx, grow, push");

        return tablePanel;
    }

    /**
     * Creates the History panel.
     */
    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 20, gap 20, flowy"));
        panel.setBackground(new Color(0xF8FAFC));

        // Title
        JLabel titleLabel = new JLabel("Transaction History");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0x1E293B));
        panel.add(titleLabel, "wrap, gapbottom 15");

        // Toolbar
        JPanel toolbar = new JPanel(new MigLayout("insets 0, gap 15"));
        toolbar.setBackground(new Color(0xF8FAFC));

        JLabel searchLabel = new JLabel("Cari:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        searchLabel.setForeground(new Color(0x1E293B));
        toolbar.add(searchLabel);

        searchField = new RoundedTextField();
        searchField.setToolTipText("Cari dalam history transaksi");
        toolbar.add(searchField, "width 250px");

        toolbar.add(new JPanel(), "push");

        refreshButton = new RoundedButton("🔄 Refresh", 20, 20);
        refreshButton.setPreferredSize(new Dimension(100, 38));
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        refreshButton.setButtonColor(new Color(0x64748B));
        toolbar.add(refreshButton);

        panel.add(toolbar, "growx, wrap, gapbottom 15");

        // History Table
        JPanel tablePanel = createHistoryTable();
        panel.add(tablePanel, "growx, grow, push, wrap");

        return panel;
    }

    /**
     * Creates the history table.
     */
    private JPanel createHistoryTable() {
        JPanel tablePanel = new JPanel(new MigLayout("insets 0, gap 0, flowy"));
        tablePanel.setBackground(new Color(0xFFFFFF));

        String[] columnNames = {"ID", "Tanggal", "Tipe", "Barang", "Jumlah", "Satuan", "Total", "User", "Keterangan"};
        Object[][] data = {
                {1001, "2026-06-29", "Masuk", "Laptop Dell XPS", 5, "Unit", 60000000, "Admin", "Pembelian dari supplier A"},
                {1002, "2026-06-29", "Keluar", "Mouse Logitech", 25, "Unit", 6250000, "User1", "Penjualan ke toko retail"},
                {1003, "2026-06-28", "Masuk", "USB Hub", 50, "Unit", 5000000, "Admin", "Pembelian bulk"},
                {1004, "2026-06-28", "Keluar", "Monitor LG 27\"", 3, "Unit", 10500000, "User2", "Transfer ke gudang cabang"},
                {1005, "2026-06-27", "Masuk", "RAM 16GB", 30, "Unit", 12000000, "Admin", "Restok inventory"},
                {1006, "2026-06-27", "Keluar", "Cable HDMI", 100, "Unit", 5000000, "User1", "Penjualan retailer online"},
                {1007, "2026-06-26", "Masuk", "SSD Samsung 1TB", 20, "Unit", 24000000, "Admin", "Pembelian dari supplier B"},
                {1008, "2026-06-26", "Keluar", "SSD Samsung 1TB", 8, "Unit", 9600000, "User3", "Penjualan ke klien korporat"},
                {1009, "2026-06-25", "Masuk", "Headset Gaming", 20, "Unit", 14000000, "Admin", "Pembelian dari supplier C"},
                {1010, "2026-06-25", "Keluar", "Headset Gaming", 12, "Unit", 8400000, "User2", "Penjualan ke klien retail"}
        };

        historyTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        historyTable = new JTable(historyTableModel);
        historyTable.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        historyTable.setRowHeight(28);
        historyTable.setBackground(new Color(0xFFFFFF));
        historyTable.setForeground(new Color(0x1E293B));
        historyTable.setGridColor(new Color(0xE2E8F0));
        historyTable.setSelectionBackground(new Color(0xE0E7FF));
        historyTable.setSelectionForeground(new Color(0x1E293B));

        int[] columnWidths = {50, 80, 60, 130, 70, 60, 110, 70, 200};
        for (int i = 0; i < columnWidths.length; i++) {
            historyTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JTableHeader header = historyTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(new Color(0xF1F5F9));
        header.setForeground(new Color(0x1E293B));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xE2E8F0)));

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBackground(new Color(0xFFFFFF));
        scrollPane.getViewport().setBackground(new Color(0xFFFFFF));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0xE2E8F0), 1));

        tablePanel.add(scrollPane, "growx, grow, push");

        return tablePanel;
    }

    /**
     * Gets the add masuk button.
     */
    public RoundedButton getAddMasukButton() {
        return addMasukButton;
    }

    /**
     * Gets the add keluar button.
     */
    public RoundedButton getAddKeluarButton() {
        return addKeluarButton;
    }

    /**
     * Gets the search field.
     */
    public RoundedTextField getSearchField() {
        return searchField;
    }

    /**
     * Gets the refresh button.
     */
    public RoundedButton getRefreshButton() {
        return refreshButton;
    }

    /**
     * Gets the history table.
     */
    public JTable getHistoryTable() {
        return historyTable;
    }

    /**
     * Gets the history table model.
     */
    public DefaultTableModel getHistoryTableModel() {
        return historyTableModel;
    }

    /**
     * Gets the tabbed pane.
     */
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
