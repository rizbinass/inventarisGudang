package view.kategori;

import com.formdev.flatlaf.FlatClientProperties;
import dao.KategoriDAO;
import model.Kategori;
import net.miginfocom.swing.MigLayout;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
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
import java.util.List;

public class KategoriPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    private static final Color BUTTON_HOVER_COLOR = new Color(226, 232, 240);
    private static final Color CARD_HOVER_COLOR = new Color(241, 245, 249);

    private final KategoriDAO kategoriDAO;
    private final JTextField searchField;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;
    private final JButton refreshButton;
    private final JTable kategoriTable;
    private final DefaultTableModel tableModel;
    private final TableRowSorter<DefaultTableModel> tableSorter;

    public KategoriPanel() {
        kategoriDAO = new KategoriDAO();
        searchField = new JTextField();
        addButton = new JButton("Tambah");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");
        kategoriTable = new JTable();
        tableModel = createTableModel();
        tableSorter = new TableRowSorter<>(tableModel);

        initializeLayout();
        initializeTable();
        initializeIcons();
        initializeActions();
        loadKategoriData();
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
                "[grow][]10[]10[]10[]10[]",
                "[]"
        ));
        toolbarPanel.setOpaque(false);

        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Cari kategori...");
        searchField.putClientProperty(FlatClientProperties.STYLE, "arc:12;margin:8,12,8,12");

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
                + "arc:18;"
                + "background:$Panel.background;"
                + "border:1,1,1,1,$Component.borderColor");
        addCardHover(tablePanel);

        JLabel titleLabel = new JLabel("Data Kategori");
        titleLabel.setIcon(FontIcon.of(FontAwesomeSolid.TAGS, 16, new Color(37, 99, 235)));
        titleLabel.setIconTextGap(10);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4");
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 0, 20));

        JScrollPane scrollPane = new JScrollPane(kategoriTable);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 20, 20));

        tablePanel.add(titleLabel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void initializeTable() {
        kategoriTable.setModel(tableModel);
        kategoriTable.setRowSorter(tableSorter);
        kategoriTable.setRowHeight(36);
        kategoriTable.setShowGrid(false);
        kategoriTable.getTableHeader().setReorderingAllowed(false);
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

    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(
                new String[]{"ID", "Nama Kategori"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void initializeActions() {
        addButton.addActionListener(event -> addKategori());
        editButton.addActionListener(event -> editKategori());
        deleteButton.addActionListener(event -> deleteKategori());
        refreshButton.addActionListener(event -> loadKategoriData());
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

    private void loadKategoriData() {
        try {
            tableModel.setRowCount(0);
            List<Kategori> kategoriList = kategoriDAO.findAll();

            for (Kategori kategori : kategoriList) {
                tableModel.addRow(new Object[]{
                        kategori.getId(),
                        kategori.getNamaKategori()
                });
            }
        } catch (SQLException exception) {
            showErrorMessage("Gagal memuat data kategori.");
        }
    }

    private void addKategori() {
        String namaKategori = showKategoriInputDialog("Tambah Kategori", "");

        if (namaKategori == null) {
            return;
        }

        try {
            kategoriDAO.create(new Kategori(0, namaKategori));
            loadKategoriData();
        } catch (SQLException exception) {
            showErrorMessage("Gagal menambahkan kategori.");
        }
    }

    private void editKategori() {
        int selectedModelRow = getSelectedModelRow();
        if (selectedModelRow < 0) {
            showWarningMessage("Pilih kategori yang akan diedit.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedModelRow, 0);
        String currentName = (String) tableModel.getValueAt(selectedModelRow, 1);
        String namaKategori = showKategoriInputDialog("Edit Kategori", currentName);

        if (namaKategori == null) {
            return;
        }

        try {
            kategoriDAO.update(new Kategori(id, namaKategori));
            loadKategoriData();
        } catch (SQLException exception) {
            showErrorMessage("Gagal mengubah kategori.");
        }
    }

    private void deleteKategori() {
        int selectedModelRow = getSelectedModelRow();
        if (selectedModelRow < 0) {
            showWarningMessage("Pilih kategori yang akan dihapus.");
            return;
        }

        int option = JOptionPane.showConfirmDialog(
                this,
                "Hapus kategori yang dipilih?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (option != JOptionPane.YES_OPTION) {
            return;
        }

        int id = (int) tableModel.getValueAt(selectedModelRow, 0);

        try {
            kategoriDAO.delete(id);
            loadKategoriData();
        } catch (SQLException exception) {
            showErrorMessage("Gagal menghapus kategori.");
        }
    }

    private String showKategoriInputDialog(String title, String initialValue) {
        JTextField namaKategoriField = new JTextField(initialValue);
        namaKategoriField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nama kategori");

        JPanel panel = new JPanel(new MigLayout(
                "wrap 1,fillx,insets 8",
                "[280!,fill]",
                "[]8[]"
        ));
        JLabel label = new JLabel("Nama Kategori");
        label.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        panel.add(label);
        panel.add(namaKategoriField);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) {
            return null;
        }

        String namaKategori = namaKategoriField.getText().trim();
        if (namaKategori.isEmpty()) {
            showWarningMessage("Nama kategori wajib diisi.");
            return null;
        }

        return namaKategori;
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
        int selectedRow = kategoriTable.getSelectedRow();

        if (selectedRow < 0) {
            return -1;
        }

        return kategoriTable.convertRowIndexToModel(selectedRow);
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

    public JTable getKategoriTable() {
        return kategoriTable;
    }
}
