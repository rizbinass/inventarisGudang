package model;

import java.time.LocalDateTime;

public class Transaksi {
    private int id;
    private Barang barang;
    private User user;
    private String jenisTransaksi;
    private int jumlah;
    private LocalDateTime tanggalTransaksi;

    public Transaksi() {
    }

    public Transaksi(int id, Barang barang, User user, String jenisTransaksi, int jumlah, LocalDateTime tanggalTransaksi) {
        this.id = id;
        this.barang = barang;
        this.user = user;
        this.jenisTransaksi = jenisTransaksi;
        this.jumlah = jumlah;
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJenisTransaksi() {
        return jenisTransaksi;
    }

    public void setJenisTransaksi(String jenisTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public LocalDateTime getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(LocalDateTime tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    @Override
    public String toString() {
        return jenisTransaksi;
    }
}
