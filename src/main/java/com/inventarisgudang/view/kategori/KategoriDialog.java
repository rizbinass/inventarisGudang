package com.inventarisgudang.view.kategori;

import com.inventarisgudang.components.RoundedButton;
import com.inventarisgudang.components.RoundedTextField;
import com.inventarisgudang.utils.UIConstants;
import net.miginfocom.swing.MigLayout;


import javax.swing.*;
import java.awt.*;

/**
 * Dialog for adding/editing Kategori (Categories).
 * Modal dialog for category form operations.
 */
public class KategoriDialog extends JDialog {
    private RoundedTextField namaField;
    private JTextArea deskripsiArea;
    private RoundedButton saveButton;
    private RoundedButton cancelButton;
    private boolean isEditMode = false;

    public KategoriDialog(JFrame parent, boolean editMode) {
        super(parent, "Tambah Kategori", true);
        this.isEditMode = editMode;

        setSize(450, 380);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle(editMode ? "Edit Kategori" : "Tambah Kategori");
        setResizable(true);

        // Create main panel
        JPanel mainPanel = new JPanel(new MigLayout(
                "insets " + UIConstants.PADDING_LARGE + ", gap " + UIConstants.SPACING_MD + ", flowy"));
        mainPanel.setBackground(UIConstants.CARD_BG);

        // Title
        JLabel titleLabel = new JLabel(editMode ? "Edit Data Kategori" : "Tambah Data Kategori Baru");
        titleLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_HEADING));
        titleLabel.setForeground(UIConstants.TEXT_PRIMARY);
        mainPanel.add(titleLabel, "wrap, gapbottom " + UIConstants.SPACING_LG);

        // Nama Kategori field
        JLabel namaLabel = new JLabel("Nama Kategori");
        namaLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_BASE));
        namaLabel.setForeground(UIConstants.TEXT_PRIMARY);
        mainPanel.add(namaLabel, "wrap, gapbottom " + UIConstants.SPACING_XS);

        namaField = new RoundedTextField();
        namaField.setToolTipText("Masukkan nama kategori");
        mainPanel.add(namaField, "growx, wrap, gapbottom " + UIConstants.SPACING_LG);

        // Deskripsi field
        JLabel deskripsiLabel = new JLabel("Deskripsi");
        deskripsiLabel.setFont(UIConstants.createBoldFont(UIConstants.FONT_BASE));
        deskripsiLabel.setForeground(UIConstants.TEXT_PRIMARY);
        mainPanel.add(deskripsiLabel, "wrap, gapbottom " + UIConstants.SPACING_XS);

        deskripsiArea = new JTextArea(4, 30);
        deskripsiArea.setFont(UIConstants.createFont(UIConstants.FONT_BASE));
        deskripsiArea.setBackground(UIConstants.INPUT_BG);
        deskripsiArea.setForeground(UIConstants.TEXT_PRIMARY);
        deskripsiArea.setLineWrap(true);
        deskripsiArea.setWrapStyleWord(true);
        deskripsiArea.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT, 1));
        deskripsiArea.setMargin(new Insets(UIConstants.INPUT_PADDING, UIConstants.INPUT_PADDING,
                UIConstants.INPUT_PADDING, UIConstants.INPUT_PADDING));

        JScrollPane scrollPane = new JScrollPane(deskripsiArea);
        scrollPane.setPreferredSize(new Dimension(400, 100));
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_LIGHT, 1));
        mainPanel.add(scrollPane, "growx, wrap, gapbottom " + UIConstants.SPACING_LG);

        // Button panel
        JPanel buttonPanel = new JPanel(new MigLayout("insets 0, gap " + UIConstants.SPACING_MD));
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
     * Gets the nama field.
     */
    public RoundedTextField getNamaField() {
        return namaField;
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
        namaField.setText("");
        deskripsiArea.setText("");
    }

    /**
     * Sets form data for editing.
     */
    public void setFormData(Object[] rowData) {
        if (rowData != null && rowData.length >= 3) {
            namaField.setText(String.valueOf(rowData[1]));
            deskripsiArea.setText(String.valueOf(rowData[2]));
        }
    }

    /**
     * Gets form data as array.
     */
    public Object[] getFormData() {
        return new Object[]{
                namaField.getText(),
                deskripsiArea.getText()
        };
    }
}

