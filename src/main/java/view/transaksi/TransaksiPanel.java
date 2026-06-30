package view.transaksi;

import com.formdev.flatlaf.FlatClientProperties;
import dao.BarangDAO;
import dao.TransaksiDAO;
import dao.UserDAO;
import model.Barang;
import model.Transaksi;
import model.User;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransaksiPanel extends JPanel {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    private static final Color BUTTON_HOVER_COLOR = new Color(226, 232, 240);
    private static final Color CARD_HOVER_COLOR = new Color(241, 245, 249);

    private final TransaksiDAO transaksiDAO;
    private final BarangDAO barangDAO;
    private final UserDAO userDAO;
    private final JTextField searchField;
    private final JComboBox<String> typeFilterComboBox;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;
    private final JButton refreshButton;
    private final JTable transaksiTable;
    private final DefaultTableModel tableModel;
    private final TableRowSorter<DefaultTableModel> tableSorter;

    public TransaksiPanel() {
        transaksiDAO = new TransaksiDAO();
        barangDAO = new BarangDAO();
        userDAO = new UserDAO();
        searchField = new JTextField();
        typeFilterComboBox = new JComboBox<>(new String[]{"Semua", "Masuk", "Keluar"});
        addButton = new JButton("Tambah");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");
        transaksiTable = new JTable();
        tableModel = createTableModel();
        tableSorter = new TableRowSorter<>(tableModel);

        initializeLayout();
        initializeTable();
        initializeIcons();
        initializeActions();
        loadTransaksiData();
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        JPanel containerPanel = new JPanel(new MigLayout(
                "fill,insets 32",
                "[grow]",
                "[]20[grow]"
        ));
        containerPanel.setOpaque(false);

        containerPanel.add(createToolbarPanel(), "growx,wrap");
        containerPanel.add(createTablePanel(), "grow");

        add(containerPanel, BorderLayout.CENTER);
    }

    private JPanel createToolbarPanel() {
        JPanel toolbarPanel = new JPanel(new MigLayout(
                "fillx,insets 0",
                "[grow][160!]10[]10[]10[]10[]",
                "[]"
        ));
        toolbarPanel.setOpaque(false);

        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Cari transaksi...");
        searchField.putClientProperty(FlatClientProperties.STYLE, "arc:12;margin:8,12,8,12");
        typeFilterComboBox.putClientProperty(FlatClientProperties.STYLE, "arc:12");

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
                + "arc:18;"
                + "background:$Panel.background;"
                + "border:1,1,1,1,$Component.borderColor");
        addCardHover(tablePanel);

        JLabel titleLabel = new JLabel("Data Transaksi");
        titleLabel.setIcon(FontIcon.of(FontAwesomeSolid.EXCHANGE_ALT, 16, new Color(37, 99, 235)));
        titleLabel.setIconTextGap(10);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4");
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 0, 20));

        JScrollPane scrollPane = new JScrollPane(transaksiTable);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 20, 20));

        tablePanel.add(titleLabel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void initializeTable() {
        transaksiTable.setModel(tableModel);
        transaksiTable.setRowSorter(tableSorter);
        transaksiTable.setRowHeight(36);
        transaksiTable.setShowGrid(false);
        transaksiTable.getTableHeader().setReorderingAllowed(false);
        transaksiTable.removeColumn(transaksiTable.getColumnModel().getColumn(0));
    }

    private void initializeIcons() {
        addButton.setIcon(FontIcon.of(FontAwesomeSolid.PLUS, 13));
        editButton.setIcon(FontIcon.of(FontAwesomeSolid.PEN, 13));
        deleteButton.setIcon(FontIcon.of(FontAwesomeSolid.TRASH_ALT, 13));
        refreshButton.setIcon(FontIcon.of(FontAwesomeSolid.SYNC_ALT, 13));

        addButton.setIconTextGap(8);
        editButton.setIconTextGap(8);
        deleteButton.setIconTextGap(8);
        refreshButton.setIconTextGap(8);
    }

    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(
                new String[]{"ID", "Barang", "User", "Jenis", "Jumlah", "Tanggal"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void initializeActions() {
        addButton.addActionListener(event -> addTransaksi());
        editButton.addActionListener(event -> editTransaksi());
        deleteButton.addActionListener(event -> deleteTransaksi());
        refreshButton.addActionListener(event -> loadTransaksiData());
        typeFilterComboBox.addActionListener(event -> filterTable());
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

    private void loadTransaksiData() {
        try {
            tableModel.setRowCount(0);
            List<Transaksi> transaksiList = transaksiDAO.findAll();

            for (Transaksi transaksi : transaksiList) {
                tableModel.addRow(new Object[]{
                        transaksi.getId(),
                        transaksi.getBarang(),
                        transaksi.getUser(),
                        transaksi.getJenisTransaksi(),
                        transaksi.getJumlah(),
                        transaksi.getTanggalTransaksi().format(DATE_TIME_FORMATTER)
                });
            }
        } catch (SQLException exception) {
            showErrorMessage("Gagal memuat data transaksi.");
        }
    }

    private void addTransaksi() {
        TransaksiForm form = new TransaksiForm();
        if (!form.loadOptions()) {
            return;
        }

        int option = JOptionPane.showConfirmDialog(
                this,
                form.getPanel(),
                "Tambah Transaksi",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        Transaksi transaksi = form.toTransaksi(0, LocalDateTime.now());
        if (transaksi == null) {
            return;
        }

        try {
            transaksiDAO.create(transaksi);
            loadTransaksiData();
        } catch (SQLException exception) {
            showErrorMessage("Gagal menambahkan transaksi. Pastikan stok barang mencukupi.");
        }
    }

    private void editTransaksi() {
        int selectedModelRow = getSelectedModelRow();
        if (selectedModelRow < 0) {
            showWarningMessage("Pilih transaksi yang akan diedit.");
            return;
        }

        TransaksiForm form = new TransaksiForm();
        if (!form.loadOptions()) {
            return;
        }

        int id = (int) tableModel.getValueAt(selectedModelRow, 0);
        LocalDateTime tanggalTransaksi = LocalDateTime.parse(
                (String) tableModel.getValueAt(selectedModelRow, 5),
                DATE_TIME_FORMATTER
        );
        form.setBarang((Barang) tableModel.getValueAt(selectedModelRow, 1));
        form.setUser((User) tableModel.getValueAt(selectedModelRow, 2));
        form.setJenisTransaksi((String) tableModel.getValueAt(selectedModelRow, 3));
        form.setJumlah((int) tableModel.getValueAt(selectedModelRow, 4));

        int option = JOptionPane.showConfirmDialog(
                this,
                form.getPanel(),
                "Edit Transaksi",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        Transaksi transaksi = form.toTransaksi(id, tanggalTransaksi);
        if (transaksi == null) {
            return;
        }

        try {
            transaksiDAO.update(transaksi);
            loadTransaksiData();
        } catch (SQLException exception) {
            showErrorMessage("Gagal mengubah transaksi. Pastikan stok barang mencukupi.");
        }
    }

    private void deleteTransaksi() {
        int selectedModelRow = getSelectedModelRow();
        if (selectedModelRow < 0) {
            showWarningMessage("Pilih transaksi yang akan dihapus.");
            return;
        }

        int option = JOptionPane.showConfirmDialog(
                this,
                "Hapus transaksi yang dipilih?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (option != JOptionPane.YES_OPTION) {
            return;
        }

        int id = (int) tableModel.getValueAt(selectedModelRow, 0);

        try {
            transaksiDAO.delete(id);
            loadTransaksiData();
        } catch (SQLException exception) {
            showErrorMessage("Gagal menghapus transaksi. Pastikan stok barang tetap valid.");
        }
    }

    private void filterTable() {
        String keyword = searchField.getText().trim();
        String selectedType = (String) typeFilterComboBox.getSelectedItem();

        RowFilter<DefaultTableModel, Object> keywordFilter = keyword.isEmpty()
                ? null
                : RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(keyword));
        RowFilter<DefaultTableModel, Object> typeFilter = "Semua".equals(selectedType)
                ? null
                : RowFilter.regexFilter("^" + selectedType + "$", 3);

        if (keywordFilter == null && typeFilter == null) {
            tableSorter.setRowFilter(null);
        } else if (keywordFilter == null) {
            tableSorter.setRowFilter(typeFilter);
        } else if (typeFilter == null) {
            tableSorter.setRowFilter(keywordFilter);
        } else {
            tableSorter.setRowFilter(RowFilter.andFilter(List.of(keywordFilter, typeFilter)));
        }
    }

    private int getSelectedModelRow() {
        int selectedRow = transaksiTable.getSelectedRow();

        if (selectedRow < 0) {
            return -1;
        }

        return transaksiTable.convertRowIndexToModel(selectedRow);
    }

    private void styleButton(JButton button) {
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:12;"
                + "focusWidth:0;"
                + "margin:9,16,9,16");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent event) {
                button.setBackground(null);
            }
        });
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

    private class TransaksiForm {
        private final JPanel panel;
        private final JComboBox<Barang> barangComboBox;
        private final JComboBox<User> userComboBox;
        private final JComboBox<String> jenisTransaksiComboBox;
        private final JSpinner jumlahSpinner;

        private TransaksiForm() {
            panel = new JPanel(new MigLayout(
                    "wrap 2,fillx,insets 8",
                    "[][300!,fill]",
                    "[]12[]12[]12[]"
            ));
            barangComboBox = new JComboBox<>();
            userComboBox = new JComboBox<>();
            jenisTransaksiComboBox = new JComboBox<>(new String[]{"Masuk", "Keluar"});
            jumlahSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

            addField("Barang", barangComboBox);
            addField("User", userComboBox);
            addField("Jenis", jenisTransaksiComboBox);
            addField("Jumlah", jumlahSpinner);
        }

        private JPanel getPanel() {
            return panel;
        }

        private boolean loadOptions() {
            try {
                barangComboBox.removeAllItems();
                userComboBox.removeAllItems();

                for (Barang barang : barangDAO.findAll()) {
                    barangComboBox.addItem(barang);
                }

                for (User user : userDAO.findAll()) {
                    userComboBox.addItem(user);
                }

                if (barangComboBox.getItemCount() == 0) {
                    showWarningMessage("Data barang belum tersedia.");
                    return false;
                }

                if (userComboBox.getItemCount() == 0) {
                    showWarningMessage("Data user belum tersedia.");
                    return false;
                }

                return true;
            } catch (SQLException exception) {
                showErrorMessage("Gagal memuat data form transaksi.");
                return false;
            }
        }

        private Transaksi toTransaksi(int id, LocalDateTime tanggalTransaksi) {
            Barang barang = (Barang) barangComboBox.getSelectedItem();
            User user = (User) userComboBox.getSelectedItem();
            String jenisTransaksi = (String) jenisTransaksiComboBox.getSelectedItem();
            int jumlah = (int) jumlahSpinner.getValue();

            if (barang == null || user == null || jenisTransaksi == null) {
                showWarningMessage("Semua field wajib diisi.");
                return null;
            }

            return new Transaksi(id, barang, user, jenisTransaksi, jumlah, tanggalTransaksi);
        }

        private void setBarang(Barang barang) {
            for (int index = 0; index < barangComboBox.getItemCount(); index++) {
                if (barangComboBox.getItemAt(index).getId() == barang.getId()) {
                    barangComboBox.setSelectedIndex(index);
                    return;
                }
            }
        }

        private void setUser(User user) {
            for (int index = 0; index < userComboBox.getItemCount(); index++) {
                if (userComboBox.getItemAt(index).getId() == user.getId()) {
                    userComboBox.setSelectedIndex(index);
                    return;
                }
            }
        }

        private void setJenisTransaksi(String jenisTransaksi) {
            jenisTransaksiComboBox.setSelectedItem(jenisTransaksi);
        }

        private void setJumlah(int jumlah) {
            jumlahSpinner.setValue(jumlah);
        }

        private void addField(String labelText, JComponent component) {
            JLabel label = new JLabel(labelText);
            label.putClientProperty(FlatClientProperties.STYLE, "font:bold");
            panel.add(label);
            panel.add(component);
        }
    }
}
