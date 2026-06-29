package dao;

import config.DatabaseConnection;
import model.Kategori;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class KategoriDAO {
    public void create(Kategori kategori) throws SQLException {
        String sql = "INSERT INTO kategori (nama_kategori) VALUES (?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, kategori.getNamaKategori());
            statement.executeUpdate();
        }
    }

    public Kategori findById(int id) throws SQLException {
        String sql = "SELECT id, nama_kategori FROM kategori WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToKategori(resultSet);
                }
            }
        }

        return null;
    }

    public List<Kategori> findAll() throws SQLException {
        String sql = "SELECT id, nama_kategori FROM kategori ORDER BY id";
        List<Kategori> kategoriList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                kategoriList.add(mapResultSetToKategori(resultSet));
            }
        }

        return kategoriList;
    }

    public void update(Kategori kategori) throws SQLException {
        String sql = "UPDATE kategori SET nama_kategori = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, kategori.getNamaKategori());
            statement.setInt(2, kategori.getId());
            statement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM kategori WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private Kategori mapResultSetToKategori(ResultSet resultSet) throws SQLException {
        return new Kategori(
                resultSet.getInt("id"),
                resultSet.getString("nama_kategori")
        );
    }
}
