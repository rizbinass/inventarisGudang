package com.inventarisgudang.view.transaksi;

import com.inventarisgudang.components.RoundedButton;
import com.inventarisgudang.components.RoundedComboBox;
import com.inventarisgudang.components.RoundedTextField;
import net.miginfocom.swing.MigLayout;


import javax.swing.*;
import java.awt.*;

/**
 * Dialog for recording Transaksi (Incoming/Outgoing).
 * Modal dialog for transaction form operations.
 */
public class TransaksiDialog extends JDialog {
    private RoundedComboBox barangCombo;
    private RoundedTextField jumlahField;
    private RoundedComboBox satuanCombo;
    private RoundedTextField hargaSatuanField;
    private JLabel totalLabel;
    private RoundedComboBox tipeTransaksiCombo;
    private RoundedTextField tujuanSupplierField;
    private JTextArea keteranganArea;
    private RoundedButton saveButton;
    private RoundedButton cancelButton;
    private boolean isIncoming = false;

    public TransaksiDialog(JFrame parent, boolean incoming) {
        super(parent, incoming ? "Tambah Barang Masuk" : "Tambah Barang Keluar", true);
        this.isIncoming = incoming;

        setSize(500, 550);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle(incoming ? "Tambah Barang Masuk" : "Tambah Barang Keluar");

        // Create main panel
        JPanel mainPanel = new JPanel(new MigLayout("insets 20, gap 15, flowy"));
        mainPanel.setBackground(new Color(0xFFFFFF));

        // Title
        JLabel titleLabel = new JLabel(incoming ? "Input Barang Masuk" : "Input Barang Keluar");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0x1E293B));
        mainPanel.add(titleLabel, "wrap, gapbottom 20");

        // Barang combo
        JLabel barangLabel = new JLabel("Barang");
        barangLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        barangLabel.setForeground(new Color(0x1E293B));
        mainPanel.add(barangLabel, "wrap, gapbottom 5");

        String[] barangItems = {"Pilih Barang", "Laptop Dell XPS", "Mouse Logitech", "Keyboard Mechanical", 
                                "Monitor LG 27\"", "USB Hub", "SSD Samsung 1TB", "RAM 16GB", "Webcam HD", 
                                "Headset Gaming", "Cable HDMI"};
        barangCombo = new RoundedComboBox(barangItems);
        mainPanel.add(barangCombo, "growx, wrap, gapbottom 15");

        // Jumlah field
        JLabel jumlahLabel = new JLabel("Jumlah");
        jumlahLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        jumlahLabel.setForeground(new Color(0x1E293B));
        mainPanel.add(jumlahLabel, "wrap, gapbottom 5");

        jumlahField = new RoundedTextField();
        jumlahField.setToolTipText("Masukkan jumlah barang");
        mainPanel.add(jumlahField, "growx, wrap, gapbottom 15");

        // Satuan combo
        JLabel satuanLabel = new JLabel("Satuan");
        satuanLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        satuanLabel.setForeground(new Color(0x1E293B));
        mainPanel.add(satuanLabel, "wrap, gapbottom 5");

        String[] satuanItems = {"Unit", "Box", "Karton", "Lusin", "Pcs", "Set"};
        satuanCombo = new RoundedComboBox(satuanItems);
        mainPanel.add(satuanCombo, "growx, wrap, gapbottom 15");

        // Harga Satuan field
        JLabel hargaLabel = new JLabel("Harga Satuan");
        hargaLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        hargaLabel.setForeground(new Color(0x1E293B));
        mainPanel.add(hargaLabel, "wrap, gapbottom 5");

        hargaSatuanField = new RoundedTextField();
        hargaSatuanField.setToolTipText("Harga per satuan");
        mainPanel.add(hargaSatuanField, "growx, wrap, gapbottom 15");

        // Total (Read-only)
        JLabel totalLabelText = new JLabel("Total");
        totalLabelText.setFont(new Font("Segoe UI", Font.BOLD, 11));
        totalLabelText.setForeground(new Color(0x1E293B));
        mainPanel.add(totalLabelText, "wrap, gapbottom 5");

        totalLabel = new JLabel("Rp 0");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalLabel.setForeground(new Color(0x2563EB));
        mainPanel.add(totalLabel, "wrap, gapbottom 15");

        // Tujuan/Supplier field
        String labelText = isIncoming ? "Supplier" : "Tujuan";
        JLabel tujuanLabel = new JLabel(labelText);
        tujuanLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        tujuanLabel.setForeground(new Color(0x1E293B));
        mainPanel.add(tujuanLabel, "wrap, gapbottom 5");

        tujuanSupplierField = new RoundedTextField();
        tujuanSupplierField.setToolTipText(isIncoming ? "Nama supplier" : "Nama tujuan/customer");
        mainPanel.add(tujuanSupplierField, "growx, wrap, gapbottom 15");

        // Keterangan field
        JLabel keteranganLabel = new JLabel("Keterangan");
        keteranganLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        keteranganLabel.setForeground(new Color(0x1E293B));
        mainPanel.add(keteranganLabel, "wrap, gapbottom 5");

        keteranganArea = new JTextArea(2, 30);
        keteranganArea.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        keteranganArea.setBackground(new Color(0xF1F5F9));
        keteranganArea.setForeground(new Color(0x1E293B));
        keteranganArea.setLineWrap(true);
        keteranganArea.setWrapStyleWord(true);
        keteranganArea.setBorder(BorderFactory.createLineBorder(new Color(0xE2E8F0), 1));

        JScrollPane scrollPane = new JScrollPane(keteranganArea);
        scrollPane.setPreferredSize(new Dimension(400, 50));
        mainPanel.add(scrollPane, "growx, wrap, gapbottom 20");

        // Button panel
        JPanel buttonPanel = new JPanel(new MigLayout("insets 0, gap 10"));
        buttonPanel.setBackground(new Color(0xFFFFFF));

        // Save button
        saveButton = new RoundedButton("Simpan", 20, 20);
        saveButton.setPreferredSize(new Dimension(140, 40));
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        buttonPanel.add(saveButton, "width 140px, height 40px");

        // Cancel button
        cancelButton = new RoundedButton("Batal", 20, 20);
        cancelButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cancelButton.setButtonColor(new Color(0x64748B));
        buttonPanel.add(cancelButton, "width 100px, height 40px");

        mainPanel.add(buttonPanel, "right, wrap");

        setContentPane(mainPanel);
    }

    /**
     * Gets the barang combo.
     */
    public RoundedComboBox getBarangCombo() {
        return barangCombo;
    }

    /**
     * Gets the jumlah field.
     */
    public RoundedTextField getJumlahField() {
        return jumlahField;
    }

    /**
     * Gets the satuan combo.
     */
    public RoundedComboBox getSatuanCombo() {
        return satuanCombo;
    }

    /**
     * Gets the harga satuan field.
     */
    public RoundedTextField getHargaSatuanField() {
        return hargaSatuanField;
    }

    /**
     * Gets the total label.
     */
    public JLabel getTotalLabel() {
        return totalLabel;
    }

    /**
     * Gets the tujuan/supplier field.
     */
    public RoundedTextField getTujuanSupplierField() {
        return tujuanSupplierField;
    }

    /**
     * Gets the keterangan area.
     */
    public JTextArea getKeteranganArea() {
        return keteranganArea;
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
     * Checks if dialog is for incoming transaction.
     */
    public boolean isIncoming() {
        return isIncoming;
    }

    /**
     * Clears all form fields.
     */
    public void clearForm() {
        barangCombo.setSelectedIndex(0);
        jumlahField.setText("");
        satuanCombo.setSelectedIndex(0);
        hargaSatuanField.setText("");
        totalLabel.setText("Rp 0");
        tujuanSupplierField.setText("");
        keteranganArea.setText("");
    }

    /**
     * Sets total amount display.
     */
    public void setTotal(long total) {
        totalLabel.setText(String.format("Rp %,d", total));
    }

    /**
     * Gets form data as array.
     */
    public Object[] getFormData() {
        return new Object[]{
                barangCombo.getSelectedItem(),
                jumlahField.getText(),
                satuanCombo.getSelectedItem(),
                hargaSatuanField.getText(),
                tujuanSupplierField.getText(),
                keteranganArea.getText()
        };
    }
}
