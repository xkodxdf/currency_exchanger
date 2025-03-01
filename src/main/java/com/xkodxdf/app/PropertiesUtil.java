package com.xkodxdf.app;

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

    public static String getProperty(Keys key) {
        return switch (key) {
            case DB_URL -> PROPERTIES.getProperty(Keys.DB_URL.getValue());
            case DB_USER -> PROPERTIES.getProperty(Keys.DB_USER.getValue());
            case DB_PASSWORD -> PROPERTIES.getProperty(Keys.DB_PASSWORD.getValue());
        };
    }

    private static void setProperties() {
        String fileName = "exchanger.properties";
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public enum Keys {
        DB_URL("db.url"),
        DB_USER("db.user"),
        DB_PASSWORD("db.password");

        private final String value;

        public String getValue() {
            return value;
        }

        Keys(String value) {
            this.value = value;
        }
    }
}
