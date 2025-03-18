package com.xkodxdf.app.model.dao;

import com.xkodxdf.app.ExceptionConverter;
import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.exception.DataNotFoundExcepton;
import com.xkodxdf.app.model.dao.interfaces.ExchangeRateDao;
import com.xkodxdf.app.model.entity.CurrencyEntity;
import com.xkodxdf.app.model.entity.ExchangeRateEntity;
import com.xkodxdf.app.util.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDaoImpl implements ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> {

    private static final String SAVE_SQL = """
            INSERT INTO exchange_rate (base_currency_id, target_currency_id, rate)
            VALUES (
            (SELECT id FROM currency WHERE code = ?),
            (SELECT id FROM currency WHERE code = ?),
            ?);
            """;
    private static final String GET_BY_CODES_SQL = """
            SELECT
                ex_r.id AS exchange_rate_id,
                base_c.id AS base_currency_id,
                base_c.full_name AS base_currency_name,
                base_c.code AS base_currency_code,
                base_c.sign AS base_currency_sign,
                target_c.id AS target_currency_id,
                target_c.full_name AS target_currency_name,
                target_c.code AS target_currency_code,
                target_c.sign AS target_currency_sign,
                ex_r.rate
            FROM exchange_rate AS ex_r
            JOIN currency base_c ON ex_r.base_currency_id = base_c.id
            JOIN currency target_c ON ex_r.target_currency_id = target_c.id
            WHERE base_c.code = ? AND target_c.code = ?;
            """;
    private static final String GET_ALL_SQL = """
            SELECT
                ex_r.id AS exchange_rate_id,
                base_c.id AS base_currency_id,
                base_c.full_name AS base_currency_name,
                base_c.code AS base_currency_code,
                base_c.sign AS base_currency_sign,
                target_c.id AS target_currency_id,
                target_c.full_name AS target_currency_name,
                target_c.code AS target_currency_code,
                target_c.sign AS target_currency_sign,
                ex_r.rate
            FROM exchange_rate AS ex_r
            JOIN currency base_c ON ex_r.base_currency_id = base_c.id
            JOIN currency target_c ON ex_r.target_currency_id = target_c.id
            ORDER BY base_c.code;
            """;
    private static final String UPDATE_SQL = """
            UPDATE exchange_rate
            SET rate = ?
            WHERE base_currency_id = (SELECT id FROM currency WHERE code = ?)
            AND target_currency_id = (SELECT id FROM currency WHERE code = ?);
            """;

    private static ExchangeRateDaoImpl INSTANCE;

    private ExchangeRateDaoImpl() {
    }

    public static ExchangeRateDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ExchangeRateDaoImpl();
        }
        return INSTANCE;
    }

    @Override
    public ExchangeRateEntity save(ExchangeRateRequestDto exchangeRateRequestDto) {
        try (Connection connection = ConnectionProvider.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, exchangeRateRequestDto.baseCurrencyCode());
            preparedStatement.setString(2, exchangeRateRequestDto.targetCurrencyCode());
            preparedStatement.setBigDecimal(3, exchangeRateRequestDto.rate());
            preparedStatement.executeUpdate();
            return get(exchangeRateRequestDto);
        } catch (SQLException e) {
            throw ExceptionConverter.convertSqlToCurrencyExchangerException(e);
        }
    }

    @Override
    public ExchangeRateEntity get(ExchangeRateRequestDto exchangeRateRequestDto) {
        try (Connection connection = ConnectionProvider.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_CODES_SQL)) {
            preparedStatement.setString(1, exchangeRateRequestDto.baseCurrencyCode());
            preparedStatement.setString(2, exchangeRateRequestDto.targetCurrencyCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new DataNotFoundExcepton();
            }
            return buildExchangeRateEntity(resultSet);
        } catch (SQLException e) {
            throw ExceptionConverter.convertSqlToCurrencyExchangerException(e);
        }
    }

    @Override
    public ExchangeRateEntity update(ExchangeRateRequestDto exchangeRateRequestDto) {
        try (Connection connection = ConnectionProvider.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setBigDecimal(1, exchangeRateRequestDto.rate());
            preparedStatement.setString(2, exchangeRateRequestDto.baseCurrencyCode());
            preparedStatement.setString(3, exchangeRateRequestDto.targetCurrencyCode());
            preparedStatement.executeUpdate();
            return get(exchangeRateRequestDto);
        } catch (SQLException e) {
            throw ExceptionConverter.convertSqlToCurrencyExchangerException(e);
        }
    }

    @Override
    public List<ExchangeRateEntity> getAll() {
        List<ExchangeRateEntity> exchangeRates = new ArrayList<>();
        try (Connection connection = ConnectionProvider.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exchangeRates.add(buildExchangeRateEntity(resultSet));
            }
        } catch (SQLException e) {
            throw ExceptionConverter.convertSqlToCurrencyExchangerException(e);
        }
        return exchangeRates;
    }

    private ExchangeRateEntity buildExchangeRateEntity(ResultSet resultSet) throws SQLException {
        return new ExchangeRateEntity(
                resultSet.getLong("exchange_rate_id"),
                new CurrencyEntity(
                        resultSet.getLong("base_currency_id"),
                        resultSet.getString("base_currency_name"),
                        resultSet.getString("base_currency_code"),
                        resultSet.getString("base_currency_sign")
                ),
                new CurrencyEntity(
                        resultSet.getLong("target_currency_id"),
                        resultSet.getString("target_currency_name"),
                        resultSet.getString("target_currency_code"),
                        resultSet.getString("target_currency_sign")
                ),
                resultSet.getBigDecimal("rate")
        );
    }
}
