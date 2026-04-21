package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL =
            System.getenv().getOrDefault("DB_URL", "jdbc:mariadb://localhost:3306/fuel_calculator_localization");

    private static final String USER =
            System.getenv().getOrDefault("DB_USER", "root");

    private static final String PASSWORD =
            System.getenv("DB_PASS");

    public static Connection getConnection() throws SQLException {
        if (PASSWORD == null || PASSWORD.isBlank()) {
            throw new SQLException("Database password is missing. Set DB_PASS environment variable.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}