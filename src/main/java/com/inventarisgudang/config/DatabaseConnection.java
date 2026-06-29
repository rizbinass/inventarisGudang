package com.inventarisgudang.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection manager for MySQL.
 * Handles connection creation and management.
 */
public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/inventaris_gudang";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    // Connection pool placeholder
    private static Connection connection;

    static {
        try {
            Class.forName(DB_DRIVER);
            logger.info("MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException ex) {
            logger.error("Failed to load MySQL JDBC Driver", ex);
        }
    }

    /**
     * Gets a database connection.
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            logger.info("Database connection established");
            return connection;
        } catch (SQLException ex) {
            logger.error("Failed to establish database connection", ex);
            throw ex;
        }
    }

    /**
     * Closes the database connection.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed");
            } catch (SQLException ex) {
                logger.error("Failed to close database connection", ex);
            }
        }
    }

    /**
     * Tests the database connection.
     * 
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                logger.info("Database connection test successful");
                closeConnection();
                return true;
            }
        } catch (SQLException ex) {
            logger.error("Database connection test failed", ex);
        }
        return false;
    }
}
