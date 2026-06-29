package dao;

import config.DatabaseConnection;
import model.Barang;
import model.Kategori;
import model.Transaksi;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {
    public void create(Transaksi transaksi) throws SQLException {
        String sql = "INSERT INTO transaksi (barang_id, user_id, jenis_transaksi, jumlah, tanggal_transaksi) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, transaksi.getBarang().getId());
            statement.setInt(2, transaksi.getUser().getId());
            statement.setString(3, transaksi.getJenisTransaksi());
            statement.setInt(4, transaksi.getJumlah());
            statement.setTimestamp(5, Timestamp.valueOf(transaksi.getTanggalTransaksi()));
            statement.executeUpdate();
        }
    }

    public Transaksi findById(int id) throws SQLException {
        String sql = """
                SELECT t.id, t.jenis_transaksi, t.jumlah, t.tanggal_transaksi,
                       b.id AS barang_id, b.kode_barang, b.nama_barang, b.stok, b.satuan,
                       k.id AS kategori_id, k.nama_kategori,
                       u.id AS user_id, u.username, u.password, u.nama
                FROM transaksi t
                JOIN barang b ON t.barang_id = b.id
                JOIN kategori k ON b.kategori_id = k.id
                JOIN users u ON t.user_id = u.id
                WHERE t.id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToTransaksi(resultSet);
                }
            }
        }

        return null;
    }

    public List<Transaksi> findAll() throws SQLException {
        String sql = """
                SELECT t.id, t.jenis_transaksi, t.jumlah, t.tanggal_transaksi,
                       b.id AS barang_id, b.kode_barang, b.nama_barang, b.stok, b.satuan,
                       k.id AS kategori_id, k.nama_kategori,
                       u.id AS user_id, u.username, u.password, u.nama
                FROM transaksi t
                JOIN barang b ON t.barang_id = b.id
                JOIN kategori k ON b.kategori_id = k.id
                JOIN users u ON t.user_id = u.id
                ORDER BY t.id
                """;
        List<Transaksi> transaksiList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                transaksiList.add(mapResultSetToTransaksi(resultSet));
            }
        }

        return transaksiList;
    }

    public void update(Transaksi transaksi) throws SQLException {
        String sql = "UPDATE transaksi SET barang_id = ?, user_id = ?, jenis_transaksi = ?, jumlah = ?, tanggal_transaksi = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, transaksi.getBarang().getId());
            statement.setInt(2, transaksi.getUser().getId());
            statement.setString(3, transaksi.getJenisTransaksi());
            statement.setInt(4, transaksi.getJumlah());
            statement.setTimestamp(5, Timestamp.valueOf(transaksi.getTanggalTransaksi()));
            statement.setInt(6, transaksi.getId());
            statement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM transaksi WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private Transaksi mapResultSetToTransaksi(ResultSet resultSet) throws SQLException {
        Kategori kategori = new Kategori(
                resultSet.getInt("kategori_id"),
                resultSet.getString("nama_kategori")
        );
        Barang barang = new Barang(
                resultSet.getInt("barang_id"),
                resultSet.getString("kode_barang"),
                resultSet.getString("nama_barang"),
                kategori,
                resultSet.getInt("stok"),
                resultSet.getString("satuan")
        );
        User user = new User(
                resultSet.getInt("user_id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("nama")
        );

        return new Transaksi(
                resultSet.getInt("id"),
                barang,
                user,
                resultSet.getString("jenis_transaksi"),
                resultSet.getInt("jumlah"),
                resultSet.getTimestamp("tanggal_transaksi").toLocalDateTime()
        );
    }
}
