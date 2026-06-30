package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        loadDriver();
        return DriverManager.getConnection(
                DatabaseConfig.URL,
                DatabaseConfig.USERNAME,
                DatabaseConfig.PASSWORD
        );
    }

    static void loadDriver() throws SQLException {
        try {
            Class.forName(DatabaseConfig.DRIVER_CLASS);
        } catch (ClassNotFoundException exception) {
            throw new SQLException("MySQL JDBC driver not found.", exception);
        }
    }
}
