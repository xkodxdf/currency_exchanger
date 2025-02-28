package com.xkodxdf.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {

    private final Properties properties;

    static {
        loadDriver();
    }

    public ConnectionProvider(Properties properties) {
        this.properties = properties;
    }

    public Connection get() {
        String url = "db.url";
        String user = "db.user";
        String password = "db.password";
        try {
            return DriverManager.getConnection(
                    properties.getProperty(url),
                    properties.getProperty(user),
                    properties.getProperty(password)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
