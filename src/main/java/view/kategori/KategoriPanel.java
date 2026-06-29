package view.kategori;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;

public class KategoriPanel extends JPanel {
    private final JTextField searchField;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;
    private final JButton refreshButton;
    private final JTable kategoriTable;

    public KategoriPanel() {
        searchField = new JTextField();
        addButton = new JButton("Tambah");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");
        kategoriTable = new JTable();

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
                "[grow][]8[]8[]8[]8[]",
                "[]"
        ));
        toolbarPanel.setOpaque(false);

        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Cari kategori...");
        searchField.putClientProperty(FlatClientProperties.STYLE, "arc:10");

        styleButton(addButton);
        styleButton(editButton);
        styleButton(deleteButton);
        styleButton(refreshButton);

        toolbarPanel.add(searchField, "growx");
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

        JLabel titleLabel = new JLabel("Data Kategori");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4");
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 0, 20));

        JScrollPane scrollPane = new JScrollPane(kategoriTable);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 20, 20));

        tablePanel.add(titleLabel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void initializeTable() {
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][]{
                        {1, "Elektronik"},
                        {2, "Aksesoris"},
                        {3, "Perangkat Kantor"},
                        {4, "ATK"},
                        {5, "Perlengkapan Gudang"}
                },
                new String[]{"ID", "Nama Kategori"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        kategoriTable.setModel(tableModel);
        kategoriTable.setRowHeight(36);
        kategoriTable.setShowGrid(false);
        kategoriTable.getTableHeader().setReorderingAllowed(false);
    }

    private void styleButton(JButton button) {
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:10;"
                + "margin:8,14,8,14");
    }

    public JTextField getSearchField() {
        return searchField;
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

    public JTable getKategoriTable() {
        return kategoriTable;
    }
}
