package com.xkodxdf.app.dao;

import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.entity.CurrencyEntity;
import com.xkodxdf.app.entity.ExchangeRateEntity;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDaoImpl implements ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> {

    private final SqlHelper sqlHelper;

    public ExchangeRateDaoImpl(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public ExchangeRateEntity save(ExchangeRateRequestDto exchangeRateRequestDto) {
        return sqlHelper.executeStatement(ExchangeRateSqlQueries.SAVE, preparedStatement -> {
            preparedStatement.setString(1, exchangeRateRequestDto.baseCurrencyCode());
            preparedStatement.setString(2, exchangeRateRequestDto.targetCurrencyCode());
            BigDecimal rate = new BigDecimal(exchangeRateRequestDto.rate());
            preparedStatement.setBigDecimal(3, rate);
            preparedStatement.executeUpdate();
            return get(exchangeRateRequestDto);
        });
    }

    @Override
    public ExchangeRateEntity get(ExchangeRateRequestDto exchangeRateRequestDto) {
        return sqlHelper.executeStatement(ExchangeRateSqlQueries.GET, preparedStatement -> {
            preparedStatement.setString(1, exchangeRateRequestDto.baseCurrencyCode());
            preparedStatement.setString(2, exchangeRateRequestDto.targetCurrencyCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildExchangeRateEntity(resultSet);
        });
    }

    @Override
    public Optional<ExchangeRateEntity> find(ExchangeRateRequestDto requestDto) {
        return sqlHelper.executeStatement(ExchangeRateSqlQueries.FIND, preparedStatement -> {
            preparedStatement.setString(1, requestDto.baseCurrencyCode());
            preparedStatement.setString(2, requestDto.targetCurrencyCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(buildExchangeRateEntity(resultSet));
        });
    }

    @Override
    public ExchangeRateEntity update(ExchangeRateRequestDto exchangeRateRequestDto) {
        return sqlHelper.executeStatement(ExchangeRateSqlQueries.UPDATE, preparedStatement -> {
            BigDecimal newRate = new BigDecimal(exchangeRateRequestDto.rate());
            preparedStatement.setBigDecimal(1, newRate);
            preparedStatement.setString(2, exchangeRateRequestDto.baseCurrencyCode());
            preparedStatement.setString(3, exchangeRateRequestDto.targetCurrencyCode());
            preparedStatement.executeUpdate();
            return get(exchangeRateRequestDto);
        });
    }

    @Override
    public List<ExchangeRateEntity> getAll() {
        return sqlHelper.executeStatement(ExchangeRateSqlQueries.GET_ALL, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ExchangeRateEntity> exchangeRates = new ArrayList<>();
            while (resultSet.next()) {
                exchangeRates.add(buildExchangeRateEntity(resultSet));
            }
            return exchangeRates;
        });
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
