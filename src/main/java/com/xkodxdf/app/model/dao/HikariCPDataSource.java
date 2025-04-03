package com.xkodxdf.app.model.dao;

import com.xkodxdf.app.exception.CurrencyExchangerException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPDataSource {

    private static final HikariDataSource DATA_SOURCE;
    private static final String CONFIG_FILE = "/hikari.properties";

    static {
        try {
            HikariConfig config = new HikariConfig(CONFIG_FILE);
            DATA_SOURCE = new HikariDataSource(config);
        } catch (Exception e) {
            throw new CurrencyExchangerException();
        }
    }

    public static HikariDataSource getDataSource() {
        return DATA_SOURCE;
    }
}
