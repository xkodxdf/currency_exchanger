package com.xkodxdf.app.util;

import com.xkodxdf.app.exception.CurrencyExchangerException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionProvider {

    static {
        loadDriver();
    }

    public static Connection get() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.getProperty(PropertiesUtil.Keys.DB_URL),
                    PropertiesUtil.getProperty(PropertiesUtil.Keys.DB_USER),
                    PropertiesUtil.getProperty(PropertiesUtil.Keys.DB_PASSWORD)
            );
        } catch (SQLException e) {
            throw new CurrencyExchangerException(e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new CurrencyExchangerException(e);
        }
    }
}
