package view.transaksi;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;

public class TransaksiPanel extends JPanel {
    private final JTextField searchField;
    private final JComboBox<String> typeFilterComboBox;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;
    private final JButton refreshButton;
    private final JTable transaksiTable;

    public TransaksiPanel() {
        searchField = new JTextField();
        typeFilterComboBox = new JComboBox<>(new String[]{"Semua", "Masuk", "Keluar"});
        addButton = new JButton("Tambah");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");
        transaksiTable = new JTable();

        initializeLayout();
        initializeTable();
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JPanel containerPanel = new JPanel(new MigLayout(
                "fill,insets 28",
                "[grow]",
                "[][grow]"
        ));
        containerPanel.setOpaque(false);

        containerPanel.add(createToolbarPanel(), "growx,wrap");
        containerPanel.add(createTablePanel(), "grow");

        add(containerPanel, BorderLayout.CENTER);
    }

    private JPanel createToolbarPanel() {
        JPanel toolbarPanel = new JPanel(new MigLayout(
                "fillx,insets 0",
                "[grow][160!]8[]8[]8[]8[]",
                "[]"
        ));
        toolbarPanel.setOpaque(false);

        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Cari transaksi...");
        searchField.putClientProperty(FlatClientProperties.STYLE, "arc:10");
        typeFilterComboBox.putClientProperty(FlatClientProperties.STYLE, "arc:10");

        styleButton(addButton);
        styleButton(editButton);
        styleButton(deleteButton);
        styleButton(refreshButton);

        toolbarPanel.add(searchField, "growx");
        toolbarPanel.add(typeFilterComboBox);
        toolbarPanel.add(addButton);
        toolbarPanel.add(editButton);
        toolbarPanel.add(deleteButton);
        toolbarPanel.add(refreshButton);

        return toolbarPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout(0, 16));
        tablePanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:12;"
                + "background:$Panel.background;"
                + "border:1,1,1,1,$Component.borderColor");

        JLabel titleLabel = new JLabel("Data Transaksi");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4");
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 0, 20));

        JScrollPane scrollPane = new JScrollPane(transaksiTable);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 20, 20));

        tablePanel.add(titleLabel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void initializeTable() {
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][]{
                        {"TRX-001", "Keyboard Mechanical", "Admin", "Masuk", 10, "2026-06-30 09:15"},
                        {"TRX-002", "Mouse Wireless", "Admin", "Keluar", 4, "2026-06-30 10:40"},
                        {"TRX-003", "Monitor 24 Inch", "Admin", "Masuk", 6, "2026-06-29 14:20"},
                        {"TRX-004", "Kabel HDMI", "Admin", "Keluar", 12, "2026-06-29 15:05"},
                        {"TRX-005", "Printer Laser", "Admin", "Masuk", 2, "2026-06-28 11:30"}
                },
                new String[]{"Kode", "Barang", "User", "Jenis", "Jumlah", "Tanggal"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        transaksiTable.setModel(tableModel);
        transaksiTable.setRowHeight(36);
        transaksiTable.setShowGrid(false);
        transaksiTable.getTableHeader().setReorderingAllowed(false);
    }

    private void styleButton(JButton button) {
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:10;"
                + "margin:8,14,8,14");
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JComboBox<String> getTypeFilterComboBox() {
        return typeFilterComboBox;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JTable getTransaksiTable() {
        return transaksiTable;
    }
}
