package com.inventarisgudang.view.barang;

import com.inventarisgudang.components.RoundedButton;
import com.inventarisgudang.components.RoundedComboBox;
import com.inventarisgudang.components.RoundedTextField;
import com.inventarisgudang.utils.UIConstants;
import net.miginfocom.swing.MigLayout;


import javax.swing.*;
import java.awt.*;

/**
 * Dialog for adding/editing Barang (Products).
 * Modal dialog for product form operations with modern ERP styling.
 */
public class BarangDialog extends JDialog {
    private RoundedTextField kodField;
    private RoundedTextField namaField;
    private RoundedComboBox kategoriCombo;
    private RoundedTextField hargaJualField;
    private RoundedTextField hargaBeliField;
    private RoundedTextField stokField;
    private RoundedTextField satuanField;
    private JTextArea deskripsiArea;
    private RoundedButton saveButton;
    private RoundedButton cancelButton;
    private boolean isEditMode = false;

    public BarangDialog(JFrame parent, boolean editMode) {
        super(parent, "Tambah Barang", true);
        this.isEditMode = editMode;

        setSize(550, 700);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle(editMode ? "Edit Barang" : "Tambah Barang");
        setResizable(true);

        // Create main panel with better styling
        JPanel mainPanel = new JPanel(new MigLayout("insets " + UIConstants.PADDING_LARGE + ", gap " + UIConstants.SPACING_MD + ", flowy"));
        mainPanel.setBackground(UIConstants.CARD_BG);

        // Title
        JLabel titleLabel = new JLabel(editMode ? "Edit Data Barang" : "Tambah Data Barang Baru");
        titleLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_HEADING));
        titleLabel.setForeground(UIConstants.TEXT_PRIMARY);
        mainPanel.add(titleLabel, "wrap, gapbottom " + UIConstants.SPACING_LG);

        // Kode field
        addFormField(mainPanel, "Kode Barang", kodField = new RoundedTextField(), "Masukkan kode barang unik");

        // Nama field
        addFormField(mainPanel, "Nama Barang", namaField = new RoundedTextField(), "Masukkan nama barang");

        // Kategori combo
        JLabel kategoriLabel = new JLabel("Kategori");
        kategoriLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_BASE));
        kategoriLabel.setForeground(UIConstants.TEXT_PRIMARY);
        mainPanel.add(kategoriLabel, "wrap, gapbottom " + UIConstants.SPACING_SM);

        String[] categories = {"Pilih Kategori", "Elektronik", "Aksesoris", "Monitor", "Storage", "Memory", "Audio", "Kabel"};
        kategoriCombo = new RoundedComboBox(categories);
        mainPanel.add(kategoriCombo, "growx, wrap, gapbottom " + UIConstants.SPACING_LG);

        // Harga Jual field
        addFormField(mainPanel, "Harga Jual", hargaJualField = new RoundedTextField(), "Masukkan harga jual");

        // Harga Beli field
        addFormField(mainPanel, "Harga Beli", hargaBeliField = new RoundedTextField(), "Masukkan harga beli");

        // Stok field
        addFormField(mainPanel, "Stok", stokField = new RoundedTextField(), "Masukkan jumlah stok");

        // Satuan field
        addFormField(mainPanel, "Satuan", satuanField = new RoundedTextField(), "Contoh: Unit, Box, Karton");

        // Deskripsi area
        JLabel deskripsiLabel = new JLabel("Deskripsi");
        deskripsiLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_BASE));
        deskripsiLabel.setForeground(UIConstants.TEXT_PRIMARY);
        mainPanel.add(deskripsiLabel, "wrap, gapbottom " + UIConstants.SPACING_SM);

        deskripsiArea = new JTextArea(4, 40);
        deskripsiArea.setFont(UIConstants.createFont(UIConstants.FONT_BASE));
        deskripsiArea.setBackground(UIConstants.INPUT_BG);
        deskripsiArea.setForeground(UIConstants.TEXT_PRIMARY);
        deskripsiArea.setLineWrap(true);
        deskripsiArea.setWrapStyleWord(true);
        deskripsiArea.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT, 1));
        deskripsiArea.setMargin(new Insets(UIConstants.INPUT_PADDING, UIConstants.INPUT_PADDING, 
                                          UIConstants.INPUT_PADDING, UIConstants.INPUT_PADDING));

        JScrollPane scrollPane = new JScrollPane(deskripsiArea);
        scrollPane.setPreferredSize(new Dimension(400, 80));
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT, 1));
        mainPanel.add(scrollPane, "growx, wrap, gapbottom " + UIConstants.SPACING_XL);

        // Button panel
        JPanel buttonPanel = new JPanel(new MigLayout("insets 0, gap " + UIConstants.SPACING_LG));
        buttonPanel.setBackground(UIConstants.CARD_BG);

        // Save button
        saveButton = new RoundedButton(editMode ? "Simpan Perubahan" : "Simpan");
        saveButton.setButtonColor(UIConstants.PRIMARY);
        saveButton.setTextColor(Color.WHITE);
        saveButton.setPreferredSize(new Dimension(150, UIConstants.BUTTON_HEIGHT_NORMAL));
        buttonPanel.add(saveButton, "width 150px, height " + UIConstants.BUTTON_HEIGHT_NORMAL + "px");

        // Cancel button
        cancelButton = new RoundedButton("Batal");
        cancelButton.setButtonColor(UIConstants.TEXT_TERTIARY);
        cancelButton.setTextColor(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(120, UIConstants.BUTTON_HEIGHT_NORMAL));
        buttonPanel.add(cancelButton, "width 120px, height " + UIConstants.BUTTON_HEIGHT_NORMAL + "px");

        mainPanel.add(buttonPanel, "right, wrap");

        setContentPane(mainPanel);
    }

    /**
     * Helper method to add a form field with label.
     */
    private void addFormField(JPanel panel, String labelText, RoundedTextField field, String tooltip) {
        JLabel label = new JLabel(labelText);
        label.setFont(UIConstants.createBoldFont(UIConstants.FONT_BASE));
        label.setForeground(UIConstants.TEXT_PRIMARY);
        panel.add(label, "wrap, gapbottom " + UIConstants.SPACING_SM);

        field.setToolTipText(tooltip);
        field.setPreferredSize(new Dimension(400, UIConstants.INPUT_HEIGHT));
        panel.add(field, "growx, wrap, gapbottom " + UIConstants.SPACING_LG);
    }

    /**
     * Gets the kode field.
     */
    public RoundedTextField getKodField() {
        return kodField;
    }

    /**
     * Gets the nama field.
     */
    public RoundedTextField getNamaField() {
        return namaField;
    }

    /**
     * Gets the kategori combo.
     */
    public RoundedComboBox getKategoriCombo() {
        return kategoriCombo;
    }

    /**
     * Gets the harga jual field.
     */
    public RoundedTextField getHargaJualField() {
        return hargaJualField;
    }

    /**
     * Gets the harga beli field.
     */
    public RoundedTextField getHargaBeliField() {
        return hargaBeliField;
    }

    /**
     * Gets the stok field.
     */
    public RoundedTextField getStokField() {
        return stokField;
    }

    /**
     * Gets the satuan field.
     */
    public RoundedTextField getSatuanField() {
        return satuanField;
    }

    /**
     * Gets the deskripsi area.
     */
    public JTextArea getDeskripsiArea() {
        return deskripsiArea;
    }

    /**
     * Gets the save button.
     */
    public RoundedButton getSaveButton() {
        return saveButton;
    }

    /**
     * Gets the cancel button.
     */
    public RoundedButton getCancelButton() {
        return cancelButton;
    }

    /**
     * Checks if dialog is in edit mode.
     */
    public boolean isEditMode() {
        return isEditMode;
    }

    /**
     * Clears all form fields.
     */
    public void clearForm() {
        kodField.setText("");
        namaField.setText("");
        kategoriCombo.setSelectedIndex(0);
        hargaJualField.setText("");
        hargaBeliField.setText("");
        stokField.setText("");
        satuanField.setText("");
        deskripsiArea.setText("");
    }

    /**
     * Sets form data for editing.
     */
    public void setFormData(Object[] rowData) {
        if (rowData != null && rowData.length >= 9) {
            kodField.setText(String.valueOf(rowData[1]));
            namaField.setText(String.valueOf(rowData[2]));
            kategoriCombo.setSelectedItem(String.valueOf(rowData[3]));
            hargaJualField.setText(String.valueOf(rowData[4]));
            hargaBeliField.setText(String.valueOf(rowData[5]));
            stokField.setText(String.valueOf(rowData[6]));
            satuanField.setText(String.valueOf(rowData[7]));
            deskripsiArea.setText(String.valueOf(rowData[8]));
        }
    }

    /**
     * Gets form data as array.
     */
    public Object[] getFormData() {
        return new Object[]{
                kodField.getText(),
                namaField.getText(),
                kategoriCombo.getSelectedItem(),
                hargaJualField.getText(),
                hargaBeliField.getText(),
                stokField.getText(),
                satuanField.getText(),
                deskripsiArea.getText()
        };
    }
}

