package com.xkodxdf.app;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Properties properties = new Properties();
        setProperties(properties);
        ConnectionProvider connectionProvider = new ConnectionProvider(properties);
        try (Connection connection = connectionProvider.get()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM currencies"
            );
            StringBuilder stringBuilder = new StringBuilder();
            while (resultSet.next()) {
                stringBuilder.append(resultSet.getString(2)).append(" | ");
                stringBuilder.append(resultSet.getString(3)).append(" | ");
                stringBuilder.append(resultSet.getString(4));
                System.out.println(stringBuilder);
                stringBuilder.setLength(0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setProperties(Properties properties) {
        String path = "src/main/resources/exchanger.properties";
        try (InputStream propFile = new FileInputStream(
                System.getProperty("user.dir")
                        + File.separator
                        + path)) {
            properties.load(propFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}