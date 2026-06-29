package com.inventarisgudang.view.barang;

import com.inventarisgudang.components.RoundedButton;
import com.inventarisgudang.components.RoundedTextField;
import com.inventarisgudang.utils.TableStyleUtil;
import com.inventarisgudang.utils.UIConstants;
import net.miginfocom.swing.MigLayout;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel for managing Barang (Products).
 * Displays product list with CRUD operations and modern ERP styling.
 */
public class BarangPanel extends JPanel {
    private RoundedTextField searchField;
    private RoundedButton addButton;
    private RoundedButton editButton;
    private RoundedButton deleteButton;
    private RoundedButton refreshButton;
    private JTable barangTable;
    private DefaultTableModel tableModel;

    public BarangPanel() {
        setLayout(new MigLayout("insets " + UIConstants.SPACING_2XL + ", gap " + UIConstants.SPACING_LG + ", flowy"));
        setBackground(UIConstants.CONTENT_BG);

        // Title
        JLabel titleLabel = new JLabel("Manajemen Barang");
        titleLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_LARGE_TITLE));
        titleLabel.setForeground(UIConstants.TEXT_PRIMARY);
        add(titleLabel, "wrap, gapbottom " + UIConstants.SPACING_XL);

        // Toolbar
        JPanel toolbarPanel = createToolbar();
        add(toolbarPanel, "growx, wrap, gapbottom " + UIConstants.SPACING_LG);

        // Table
        JPanel tablePanel = createTable();
        add(tablePanel, "growx, grow, push, wrap");
    }

    /**
     * Creates the toolbar with search and action buttons.
     */
    private JPanel createToolbar() {
        JPanel toolbar = new JPanel(new MigLayout("insets " + UIConstants.PADDING_NORMAL + ", gap " + UIConstants.SPACING_LG));
        toolbar.setBackground(UIConstants.PANEL_BG);
        toolbar.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT, 1));
        toolbar.setOpaque(true);

        // Search field label and input
        JLabel searchLabel = new JLabel("Cari:");
        searchLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_BASE));
        searchLabel.setForeground(UIConstants.TEXT_SECONDARY);
        toolbar.add(searchLabel);

        searchField = new RoundedTextField();
        searchField.setToolTipText("Cari barang berdasarkan nama atau kode");
        searchField.setPreferredSize(new Dimension(300, UIConstants.INPUT_HEIGHT));
        toolbar.add(searchField);

        // Spacer
        toolbar.add(new JPanel(), "push");

        // Add button
        addButton = new RoundedButton("+ Tambah");
        addButton.setButtonColor(UIConstants.PRIMARY);
        addButton.setTextColor(Color.WHITE);
        addButton.setPreferredSize(new Dimension(120, UIConstants.BUTTON_HEIGHT_NORMAL));
        toolbar.add(addButton);

        // Edit button
        editButton = new RoundedButton("✎ Edit");
        editButton.setButtonColor(UIConstants.SECONDARY);
        editButton.setTextColor(Color.WHITE);
        editButton.setPreferredSize(new Dimension(110, UIConstants.BUTTON_HEIGHT_NORMAL));
        toolbar.add(editButton);

        // Delete button
        deleteButton = new RoundedButton("🗑 Hapus");
        deleteButton.setButtonColor(UIConstants.ACCENT_RED);
        deleteButton.setTextColor(Color.WHITE);
        deleteButton.setPreferredSize(new Dimension(115, UIConstants.BUTTON_HEIGHT_NORMAL));
        toolbar.add(deleteButton);

        // Refresh button
        refreshButton = new RoundedButton("🔄 Refresh");
        refreshButton.setButtonColor(UIConstants.TEXT_TERTIARY);
        refreshButton.setTextColor(Color.WHITE);
        refreshButton.setPreferredSize(new Dimension(120, UIConstants.BUTTON_HEIGHT_NORMAL));
        toolbar.add(refreshButton);

        return toolbar;
    }

    /**
     * Creates the data table.
     */
    private JPanel createTable() {
        JPanel tablePanel = new JPanel(new MigLayout("insets 0, gap 0, flowy"));
        tablePanel.setBackground(UIConstants.CARD_BG);

        // Create table with dummy data
        String[] columnNames = {"ID", "Kode", "Nama Barang", "Kategori", "Harga Jual", "Harga Beli", "Stok", "Satuan", "Deskripsi"};
        Object[][] data = {
                {1, "BR001", "Laptop Dell XPS", "Elektronik", 15000000, 12000000, 5, "Unit", "Laptop 15 inch i7"},
                {2, "BR002", "Mouse Logitech", "Aksesoris", 250000, 150000, 45, "Unit", "Wireless mouse"},
                {3, "BR003", "Keyboard Mechanical", "Aksesoris", 800000, 500000, 20, "Unit", "RGB mechanical keyboard"},
                {4, "BR004", "Monitor LG 27\"", "Monitor", 3500000, 2800000, 8, "Unit", "4K monitor"},
                {5, "BR005", "USB Hub", "Aksesoris", 200000, 100000, 60, "Unit", "7 port USB 3.0"},
                {6, "BR006", "SSD Samsung 1TB", "Storage", 1200000, 900000, 25, "Unit", "NVMe SSD"},
                {7, "BR007", "RAM 16GB", "Memory", 600000, 400000, 35, "Unit", "DDR4 16GB"},
                {8, "BR008", "Webcam HD", "Elektronik", 500000, 300000, 15, "Unit", "1080p webcam"},
                {9, "BR009", "Headset Gaming", "Audio", 700000, 450000, 22, "Unit", "Gaming headset"},
                {10, "BR010", "Cable HDMI", "Kabel", 50000, 25000, 150, "Unit", "HDMI 2.1 cable"}
        };

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        barangTable = new JTable(tableModel);
        
        // Apply modern table styling
        TableStyleUtil.styleTable(barangTable);
        barangTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Column widths
        int[] columnWidths = {50, 70, 150, 100, 110, 110, 60, 70, 200};
        for (int i = 0; i < columnWidths.length; i++) {
            barangTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(barangTable);
        scrollPane.setBackground(UIConstants.CARD_BG);
        scrollPane.getViewport().setBackground(UIConstants.CARD_BG);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT, 1));

        tablePanel.add(scrollPane, "growx, grow, push");

        return tablePanel;
    }

    /**
     * Gets the search field.
     */
    public RoundedTextField getSearchField() {
        return searchField;
    }

    /**
     * Gets the add button.
     */
    public RoundedButton getAddButton() {
        return addButton;
    }

    /**
     * Gets the edit button.
     */
    public RoundedButton getEditButton() {
        return editButton;
    }

    /**
     * Gets the delete button.
     */
    public RoundedButton getDeleteButton() {
        return deleteButton;
    }

    /**
     * Gets the refresh button.
     */
    public RoundedButton getRefreshButton() {
        return refreshButton;
    }

    /**
     * Gets the barang table.
     */
    public JTable getBarangTable() {
        return barangTable;
    }

    /**
     * Gets the table model.
     */
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    /**
     * Gets the selected row index.
     */
    public int getSelectedRow() {
        return barangTable.getSelectedRow();
    }

    /**
     * Gets the selected row data.
     */
    public Object[] getSelectedRowData() {
        int selectedRow = barangTable.getSelectedRow();
        if (selectedRow >= 0) {
            Object[] rowData = new Object[barangTable.getColumnCount()];
            for (int i = 0; i < barangTable.getColumnCount(); i++) {
                rowData[i] = barangTable.getValueAt(selectedRow, i);
            }
            return rowData;
        }
        return null;
    }

    /**
     * Adds a new row to the table.
     */
    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    /**
     * Updates a row in the table.
     */
    public void updateRow(int row, Object[] rowData) {
        for (int i = 0; i < rowData.length; i++) {
            tableModel.setValueAt(rowData[i], row, i);
        }
    }

    /**
     * Deletes a row from the table.
     */
    public void deleteRow(int row) {
        if (row >= 0) {
            tableModel.removeRow(row);
        }
    }

    /**
     * Clears all rows from the table.
     */
    public void clearTable() {
        tableModel.setRowCount(0);
    }
}
