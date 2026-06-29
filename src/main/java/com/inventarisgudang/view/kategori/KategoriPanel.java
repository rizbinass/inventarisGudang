package com.inventarisgudang.view.kategori;

import com.inventarisgudang.components.RoundedButton;
import com.inventarisgudang.components.RoundedTextField;
import com.inventarisgudang.utils.TableStyleUtil;
import com.inventarisgudang.utils.UIConstants;
import net.miginfocom.swing.MigLayout;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel for managing Kategori (Categories).
 * Displays category list with CRUD operations and modern ERP styling.
 */
public class KategoriPanel extends JPanel {
    private RoundedTextField searchField;
    private RoundedButton addButton;
    private RoundedButton editButton;
    private RoundedButton deleteButton;
    private RoundedButton refreshButton;
    private JTable kategoriTable;
    private DefaultTableModel tableModel;

    public KategoriPanel() {
        setLayout(new MigLayout("insets " + UIConstants.SPACING_2XL + ", gap " + UIConstants.SPACING_LG + ", flowy"));
        setBackground(UIConstants.CONTENT_BG);

        // Title
        JLabel titleLabel = new JLabel("Manajemen Kategori");
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
        searchField.setToolTipText("Cari kategori berdasarkan nama");
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
        String[] columnNames = {"ID", "Nama Kategori", "Deskripsi", "Jumlah Barang"};
        Object[][] data = {
                {1, "Elektronik", "Perangkat elektronik dan gadget", 45},
                {2, "Aksesoris", "Aksesori komputer dan elektronik", 78},
                {3, "Monitor", "Monitor dan display devices", 12},
                {4, "Storage", "Penyimpanan data dan media", 34},
                {5, "Memory", "RAM dan memori tambahan", 56},
                {6, "Audio", "Perangkat audio dan speaker", 23},
                {7, "Kabel", "Kabel dan konektor berbagai tipe", 145},
                {8, "Printer", "Printer dan mesin cetak", 8},
                {9, "Network", "Perangkat jaringan dan WiFi", 19},
                {10, "Konsumsi", "Barang habis pakai", 234}
        };

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        kategoriTable = new JTable(tableModel);
        
        // Apply modern table styling
        TableStyleUtil.styleTable(kategoriTable);
        kategoriTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Column widths
        int[] columnWidths = {50, 150, 350, 120};
        for (int i = 0; i < columnWidths.length; i++) {
            kategoriTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(kategoriTable);
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
     * Gets the kategori table.
     */
    public JTable getKategoriTable() {
        return kategoriTable;
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
        return kategoriTable.getSelectedRow();
    }

    /**
     * Gets the selected row data.
     */
    public Object[] getSelectedRowData() {
        int selectedRow = kategoriTable.getSelectedRow();
        if (selectedRow >= 0) {
            Object[] rowData = new Object[kategoriTable.getColumnCount()];
            for (int i = 0; i < kategoriTable.getColumnCount(); i++) {
                rowData[i] = kategoriTable.getValueAt(selectedRow, i);
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
