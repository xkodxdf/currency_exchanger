package com.xkodxdf.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    static {
        loadDriver();
    }

    public Connection get() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.getProperty(PropertiesUtil.Keys.DB_URL),
                    PropertiesUtil.getProperty(PropertiesUtil.Keys.DB_USER),
                    PropertiesUtil.getProperty(PropertiesUtil.Keys.DB_PASSWORD)
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
