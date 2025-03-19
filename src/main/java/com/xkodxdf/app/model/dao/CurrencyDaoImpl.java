package com.xkodxdf.app.model.dao;


import com.xkodxdf.app.ExceptionConverter;
import com.xkodxdf.app.dto.CurrencyRequestDto;
import com.xkodxdf.app.exception.DataNotFoundExcepton;
import com.xkodxdf.app.model.dao.interfaces.CurrencyDao;
import com.xkodxdf.app.model.entity.CurrencyEntity;
import com.xkodxdf.app.util.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDaoImpl implements CurrencyDao<CurrencyRequestDto, CurrencyEntity> {

    private static final String ID_COLUMN_LABEL = "id";
    private static final String SIGN_COLUMN_LABEL = "sign";
    private static final String CODE_COLUMN_LABEL = "code";
    private static final String NAME_COLUMN_LABEL = "full_name";

    private static final String SAVE_SQL = """
            INSERT INTO currency (sign, code, full_name)
            VALUES (?, ?, ?);""";

    private static final String GET_BY_CODE_SQL = """
            SELECT id, sign, code, full_name
            FROM currency
            WHERE code = ?;""";

    private static final String DELETE_BY_CODE_SQL = """
            DELETE FROM currency
            WHERE code = ?;
            """;

    private static final String GET_ALL_SQL = """
            SELECT id, sign, code, full_name
            FROM currency
            ORDER BY code;
            """;

    private static CurrencyDaoImpl INSTANCE;

    private CurrencyDaoImpl() {
    }

    public static CurrencyDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CurrencyDaoImpl();
        }
        return INSTANCE;
    }

    @Override
    public CurrencyEntity save(CurrencyRequestDto requestDto) {
        try (Connection connection = ConnectionProvider.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, requestDto.sign());
            preparedStatement.setString(2, requestDto.code());
            preparedStatement.setString(3, requestDto.name());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            long currencyId = generatedKeys.getLong(ID_COLUMN_LABEL);
            return new CurrencyEntity(currencyId, requestDto);
        } catch (SQLException e) {
            throw ExceptionConverter.toCurrencyExchangerException(e);
        }
    }

    @Override
    public CurrencyEntity get(CurrencyRequestDto requestDto) {
        try (Connection connection = ConnectionProvider.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_CODE_SQL)) {
            preparedStatement.setString(1, requestDto.code());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new DataNotFoundExcepton();
            }
            return buildCurrency(resultSet);
        } catch (SQLException e) {
            throw ExceptionConverter.toCurrencyExchangerException(e);
        }
    }

    @Override
    public CurrencyEntity delete(CurrencyRequestDto requestDto) {
        try (Connection connection = ConnectionProvider.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_CODE_SQL)) {
            preparedStatement.setString(1, requestDto.code());
            CurrencyEntity currencyToDelete = get(requestDto);
            preparedStatement.executeUpdate();
            return currencyToDelete;
        } catch (SQLException e) {
            throw ExceptionConverter.toCurrencyExchangerException(e);
        }
    }

    @Override
    public List<CurrencyEntity> getAll() {
        try (Connection connection = ConnectionProvider.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CurrencyEntity> currencies = new ArrayList<>();
            while (resultSet.next()) {
                currencies.add(buildCurrency(resultSet));
            }
            return currencies;
        } catch (SQLException e) {
            throw ExceptionConverter.toCurrencyExchangerException(e);
        }
    }

    private CurrencyEntity buildCurrency(ResultSet resultSet) throws SQLException {
        return new CurrencyEntity(
                resultSet.getLong(ID_COLUMN_LABEL),
                resultSet.getString(NAME_COLUMN_LABEL),
                resultSet.getString(CODE_COLUMN_LABEL),
                resultSet.getString(SIGN_COLUMN_LABEL)
        );
    }
}
