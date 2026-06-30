package dao;

import config.DatabaseConnection;
import model.Barang;
import model.Kategori;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BarangDAO {
    public void create(Barang barang) throws SQLException {
        validateBarang(barang);
        String sql = "INSERT INTO barang (kode_barang, nama_barang, kategori_id, stok, satuan) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, barang.getKodeBarang());
            statement.setString(2, barang.getNamaBarang());
            statement.setInt(3, barang.getKategori().getId());
            statement.setInt(4, barang.getStok());
            statement.setString(5, barang.getSatuan());
            statement.executeUpdate();
        }
    }

    public Barang findById(int id) throws SQLException {
        String sql = """
                SELECT b.id, b.kode_barang, b.nama_barang, b.stok, b.satuan,
                       k.id AS kategori_id, k.nama_kategori
                FROM barang b
                JOIN kategori k ON b.kategori_id = k.id
                WHERE b.id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToBarang(resultSet);
                }
            }
        }

        return null;
    }

    public List<Barang> findAll() throws SQLException {
        String sql = """
                SELECT b.id, b.kode_barang, b.nama_barang, b.stok, b.satuan,
                       k.id AS kategori_id, k.nama_kategori
                FROM barang b
                JOIN kategori k ON b.kategori_id = k.id
                ORDER BY b.id
                """;
        List<Barang> barangList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    barangList.add(mapResultSetToBarang(resultSet));
                }
            }
        }

        return barangList;
    }

    public void update(Barang barang) throws SQLException {
        validateBarang(barang);
        String sql = "UPDATE barang SET kode_barang = ?, nama_barang = ?, kategori_id = ?, stok = ?, satuan = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, barang.getKodeBarang());
            statement.setString(2, barang.getNamaBarang());
            statement.setInt(3, barang.getKategori().getId());
            statement.setInt(4, barang.getStok());
            statement.setString(5, barang.getSatuan());
            statement.setInt(6, barang.getId());
            statement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM barang WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private Barang mapResultSetToBarang(ResultSet resultSet) throws SQLException {
        Kategori kategori = new Kategori(
                resultSet.getInt("kategori_id"),
                resultSet.getString("nama_kategori")
        );

        return new Barang(
                resultSet.getInt("id"),
                resultSet.getString("kode_barang"),
                resultSet.getString("nama_barang"),
                kategori,
                resultSet.getInt("stok"),
                resultSet.getString("satuan")
        );
    }

    private void validateBarang(Barang barang) {
        Objects.requireNonNull(barang, "barang");
        Objects.requireNonNull(barang.getKategori(), "barang.kategori");
        if (barang.getStok() < 0) {
            throw new IllegalArgumentException("barang.stok must be greater than or equal to zero");
        }
    }
}
