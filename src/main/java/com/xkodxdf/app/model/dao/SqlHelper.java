package com.xkodxdf.app.model.dao;

import com.xkodxdf.app.ExceptionConverter;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SqlHelper {

    private final HikariDataSource dataSource;

    public SqlHelper(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T executeStatement(String query, SqlExecutor<T> executor) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return executor.execute(preparedStatement);
        } catch (Exception e) {
            throw ExceptionConverter.toCurrencyExchangerException(e);
        }
    }

    public <T> T executeStatement(String query, int statementOption, SqlExecutor<T> executor) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, statementOption)) {
            return executor.execute(preparedStatement);
        } catch (Exception e) {
            throw ExceptionConverter.toCurrencyExchangerException(e);
        }
    }
}
