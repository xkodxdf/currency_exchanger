package com.xkodxdf.app;

import com.xkodxdf.app.exception.CurrencyExchangerException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    static {
        setProperties();
    }

    private PropertiesUtil() {
    }

    public static String getProperty(PropertyKeys key) {
        return switch (key) {
            case DB_URL -> PROPERTIES.getProperty(PropertyKeys.DB_URL.getKey());
            case DB_USER -> PROPERTIES.getProperty(PropertyKeys.DB_USER.getKey());
            case DB_PASSWORD -> PROPERTIES.getProperty(PropertyKeys.DB_PASSWORD.getKey());
            case DB_POOL_SIZE -> PROPERTIES.getProperty(PropertyKeys.DB_POOL_SIZE.getKey());
        };
    }

    private static void setProperties() {
        String fileName = "exchanger.properties";
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new CurrencyExchangerException();
        }
    }
}
