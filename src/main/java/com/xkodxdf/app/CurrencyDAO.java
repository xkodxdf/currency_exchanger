package com.xkodxdf.app;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CurrencyDAO {

    private final String idColumnLabel = "id";
    private final String signColumnLabel = "sign";
    private final String codeColumnLabel = "code";
    private final String fullNameColumnLabel = "full_name";
    private final ConnectionProvider connectionProvider;

    public CurrencyDAO(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void add(Currency currency) {
        try (Connection connection = connectionProvider.get()) {
            String addCurrencyQuery = """
                    INSERT INTO currency (sign, code, full_name)
                    VALUES (?, ?, ?);""";
            PreparedStatement preparedStatement = connection.prepareStatement(addCurrencyQuery);
            preparedStatement.setString(1, currency.sign());
            preparedStatement.setString(2, currency.code());
            preparedStatement.setString(3, currency.fullName());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CurrencyDTO get(String code) {
        try (Connection connection = connectionProvider.get()) {
            String getByCodeQuery = """
                    SELECT id, sign, code, full_name
                    FROM currency
                    WHERE code = ?;""";
            PreparedStatement preparedStatement = connection.prepareStatement(getByCodeQuery);
            preparedStatement.setString(1, code);
            ResultSet result = preparedStatement.executeQuery();
            if (!result.next()) {
                throw new NoSuchElementException();
            }
            return new CurrencyDTO(
                    result.getInt(idColumnLabel),
                    result.getString(signColumnLabel),
                    result.getString(codeColumnLabel),
                    result.getString(fullNameColumnLabel));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CurrencyDTO> getAllSorted() {
        try (Connection connection = connectionProvider.get()) {
            String getAllQuery = """
                    SELECT id, sign, code, full_name
                    FROM currency;
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(getAllQuery);
            ResultSet result = preparedStatement.executeQuery();
            List<CurrencyDTO> currenciesDTO = new ArrayList<>();
            while (result.next()) {
                currenciesDTO.add(new CurrencyDTO(
                        result.getInt(idColumnLabel),
                        result.getString(signColumnLabel),
                        result.getString(codeColumnLabel),
                        result.getString(fullNameColumnLabel)));
            }
            currenciesDTO.sort(Comparator.comparing(CurrencyDTO::code));
            return currenciesDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
