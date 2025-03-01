package com.xkodxdf.app;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        ConnectionProvider connectionProvider = new ConnectionProvider();
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
}