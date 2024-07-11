package dev.jlkeesh.httpserver.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceConfig {
    public static Connection connection;
    private static final String url = SettingsConfig.get("datasource.url");
    private static final String username = SettingsConfig.get("datasource.username");
    private static final String password = SettingsConfig.get("datasource.password");

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return connection;
    }
}
