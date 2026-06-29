package view.barang;

import com.formdev.flatlaf.FlatClientProperties;
import dao.BarangDAO;
import dao.KategoriDAO;
import model.Barang;
import model.Kategori;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.SQLException;
import java.util.List;

public class BarangPanel extends JPanel {
    private final BarangDAO barangDAO;
    private final KategoriDAO kategoriDAO;
    private final JTextField searchField;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;
    private final JButton refreshButton;
    private final JTable barangTable;
    private final DefaultTableModel tableModel;
    private final TableRowSorter<DefaultTableModel> tableSorter;

    public BarangPanel() {
        barangDAO = new BarangDAO();
        kategoriDAO = new KategoriDAO();
        searchField = new JTextField();
        addButton = new JButton("Tambah");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");
        barangTable = new JTable();
        tableModel = createTableModel();
        tableSorter = new TableRowSorter<>(tableModel);

        initializeLayout();
        initializeTable();
        initializeActions();
        loadBarangData();
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

        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Cari barang...");
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

        JLabel titleLabel = new JLabel("Data Barang");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4");
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 0, 20));

        JScrollPane scrollPane = new JScrollPane(barangTable);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 20, 20));

        tablePanel.add(titleLabel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void initializeTable() {
        barangTable.setModel(tableModel);
        barangTable.setRowSorter(tableSorter);
        barangTable.setRowHeight(36);
        barangTable.setShowGrid(false);
        barangTable.getTableHeader().setReorderingAllowed(false);
        barangTable.removeColumn(barangTable.getColumnModel().getColumn(0));
    }

    private void styleButton(JButton button) {
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:10;"
                + "margin:8,14,8,14");
    }

    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(
                new String[]{"ID", "Kode", "Nama Barang", "Kategori", "Stok", "Satuan"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void initializeActions() {
        addButton.addActionListener(event -> addBarang());
        editButton.addActionListener(event -> editBarang());
        deleteButton.addActionListener(event -> deleteBarang());
        refreshButton.addActionListener(event -> loadBarangData());
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                filterTable();
            }
        });
    }

    private void loadBarangData() {
        try {
            tableModel.setRowCount(0);
            List<Barang> barangList = barangDAO.findAll();

            for (Barang barang : barangList) {
                tableModel.addRow(new Object[]{
                        barang.getId(),
                        barang.getKodeBarang(),
                        barang.getNamaBarang(),
                        barang.getKategori(),
                        barang.getStok(),
                        barang.getSatuan()
                });
            }
        } catch (SQLException exception) {
            showErrorMessage("Gagal memuat data barang.");
        }
    }

    private void addBarang() {
        BarangForm form = new BarangForm();

        if (!form.loadKategoriOptions()) {
            return;
        }

        int option = JOptionPane.showConfirmDialog(
                this,
                form.getPanel(),
                "Tambah Barang",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        Barang barang = form.toBarang(0);
        if (barang == null) {
            return;
        }

        try {
            barangDAO.create(barang);
            loadBarangData();
        } catch (SQLException exception) {
            showErrorMessage("Gagal menambahkan barang.");
        }
    }

    private void editBarang() {
        int selectedModelRow = getSelectedModelRow();
        if (selectedModelRow < 0) {
            showWarningMessage("Pilih barang yang akan diedit.");
            return;
        }

        BarangForm form = new BarangForm();
        if (!form.loadKategoriOptions()) {
            return;
        }

        int id = (int) tableModel.getValueAt(selectedModelRow, 0);
        form.setKodeBarang((String) tableModel.getValueAt(selectedModelRow, 1));
        form.setNamaBarang((String) tableModel.getValueAt(selectedModelRow, 2));
        form.setKategori((Kategori) tableModel.getValueAt(selectedModelRow, 3));
        form.setStok((int) tableModel.getValueAt(selectedModelRow, 4));
        form.setSatuan((String) tableModel.getValueAt(selectedModelRow, 5));

        int option = JOptionPane.showConfirmDialog(
                this,
                form.getPanel(),
                "Edit Barang",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        Barang barang = form.toBarang(id);
        if (barang == null) {
            return;
        }

        try {
            barangDAO.update(barang);
            loadBarangData();
        } catch (SQLException exception) {
            showErrorMessage("Gagal mengubah barang.");
        }
    }

    private void deleteBarang() {
        int selectedModelRow = getSelectedModelRow();
        if (selectedModelRow < 0) {
            showWarningMessage("Pilih barang yang akan dihapus.");
            return;
        }

        int option = JOptionPane.showConfirmDialog(
                this,
                "Hapus barang yang dipilih?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (option != JOptionPane.YES_OPTION) {
            return;
        }

        int id = (int) tableModel.getValueAt(selectedModelRow, 0);

        try {
            barangDAO.delete(id);
            loadBarangData();
        } catch (SQLException exception) {
            showErrorMessage("Gagal menghapus barang.");
        }
    }

    private void filterTable() {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            tableSorter.setRowFilter(null);
            return;
        }

        tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(keyword)));
    }

    private int getSelectedModelRow() {
        int selectedRow = barangTable.getSelectedRow();

        if (selectedRow < 0) {
            return -1;
        }

        return barangTable.convertRowIndexToModel(selectedRow);
    }

    private void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                message,
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
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

    public JTable getBarangTable() {
        return barangTable;
    }

    private class BarangForm {
        private final JPanel panel;
        private final JTextField kodeBarangField;
        private final JTextField namaBarangField;
        private final JComboBox<Kategori> kategoriComboBox;
        private final JSpinner stokSpinner;
        private final JTextField satuanField;

        private BarangForm() {
            panel = new JPanel(new MigLayout(
                    "wrap 2,fillx,insets 8",
                    "[][280!,fill]",
                    "[]12[]12[]12[]12[]"
            ));
            kodeBarangField = new JTextField();
            namaBarangField = new JTextField();
            kategoriComboBox = new JComboBox<>();
            stokSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
            satuanField = new JTextField();

            addField("Kode Barang", kodeBarangField);
            addField("Nama Barang", namaBarangField);
            addField("Kategori", kategoriComboBox);
            addField("Stok", stokSpinner);
            addField("Satuan", satuanField);
        }

        private JPanel getPanel() {
            return panel;
        }

        private boolean loadKategoriOptions() {
            try {
                kategoriComboBox.removeAllItems();

                for (Kategori kategori : kategoriDAO.findAll()) {
                    kategoriComboBox.addItem(kategori);
                }

                if (kategoriComboBox.getItemCount() == 0) {
                    showWarningMessage("Data kategori belum tersedia.");
                    return false;
                }

                return true;
            } catch (SQLException exception) {
                showErrorMessage("Gagal memuat data kategori.");
                return false;
            }
        }

        private Barang toBarang(int id) {
            String kodeBarang = kodeBarangField.getText().trim();
            String namaBarang = namaBarangField.getText().trim();
            Kategori kategori = (Kategori) kategoriComboBox.getSelectedItem();
            int stok = (int) stokSpinner.getValue();
            String satuan = satuanField.getText().trim();

            if (kodeBarang.isEmpty() || namaBarang.isEmpty() || kategori == null || satuan.isEmpty()) {
                showWarningMessage("Semua field wajib diisi.");
                return null;
            }

            return new Barang(id, kodeBarang, namaBarang, kategori, stok, satuan);
        }

        private void setKodeBarang(String kodeBarang) {
            kodeBarangField.setText(kodeBarang);
        }

        private void setNamaBarang(String namaBarang) {
            namaBarangField.setText(namaBarang);
        }

        private void setKategori(Kategori kategori) {
            for (int index = 0; index < kategoriComboBox.getItemCount(); index++) {
                if (kategoriComboBox.getItemAt(index).getId() == kategori.getId()) {
                    kategoriComboBox.setSelectedIndex(index);
                    return;
                }
            }
        }

        private void setStok(int stok) {
            stokSpinner.setValue(stok);
        }

        private void setSatuan(String satuan) {
            satuanField.setText(satuan);
        }

        private void addField(String labelText, JComponent component) {
            JLabel label = new JLabel(labelText);
            label.putClientProperty(FlatClientProperties.STYLE, "font:bold");
            panel.add(label);
            panel.add(component);
        }
    }
}
