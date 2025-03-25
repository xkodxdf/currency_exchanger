package com.xkodxdf.app.model.dao;

import com.xkodxdf.app.ExceptionConverter;
import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.model.dao.interfaces.ExchangeRateDao;
import com.xkodxdf.app.model.entity.CurrencyEntity;
import com.xkodxdf.app.model.entity.ExchangeRateEntity;
import com.xkodxdf.app.util.ConnectionProvider;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDaoImpl implements ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> {


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
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ExchangeRateSqlQueries.SAVE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, exchangeRateRequestDto.baseCurrencyCode());
            preparedStatement.setString(2, exchangeRateRequestDto.targetCurrencyCode());
            BigDecimal rate = new BigDecimal(exchangeRateRequestDto.rate());
            preparedStatement.setBigDecimal(3, rate);
            preparedStatement.executeUpdate();
            return get(exchangeRateRequestDto);
        } catch (Exception e) {
            throw ExceptionConverter.toCurrencyExchangerException(e);
        }
    }

    @Override
    public ExchangeRateEntity get(ExchangeRateRequestDto exchangeRateRequestDto) {
        try (Connection connection = ConnectionProvider.get();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ExchangeRateSqlQueries.GET)) {
            preparedStatement.setString(1, exchangeRateRequestDto.baseCurrencyCode());
            preparedStatement.setString(2, exchangeRateRequestDto.targetCurrencyCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildExchangeRateEntity(resultSet);
        } catch (Exception e) {
            throw ExceptionConverter.toCurrencyExchangerException(e);
        }
    }

    @Override
    public Optional<ExchangeRateEntity> find(ExchangeRateRequestDto requestDto) {
        try (Connection connection = ConnectionProvider.get();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ExchangeRateSqlQueries.FIND)) {
            preparedStatement.setString(1, requestDto.baseCurrencyCode());
            preparedStatement.setString(2, requestDto.targetCurrencyCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(buildExchangeRateEntity(resultSet));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public ExchangeRateEntity update(ExchangeRateRequestDto exchangeRateRequestDto) {
        try (Connection connection = ConnectionProvider.get();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ExchangeRateSqlQueries.UPDATE)) {
            BigDecimal newRate = new BigDecimal(exchangeRateRequestDto.rate());
            preparedStatement.setBigDecimal(1, newRate);
            preparedStatement.setString(2, exchangeRateRequestDto.baseCurrencyCode());
            preparedStatement.setString(3, exchangeRateRequestDto.targetCurrencyCode());
            preparedStatement.executeUpdate();
            return get(exchangeRateRequestDto);
        } catch (SQLException e) {
            throw ExceptionConverter.toCurrencyExchangerException(e);
        }
    }

    @Override
    public List<ExchangeRateEntity> getAll() {
        List<ExchangeRateEntity> exchangeRates = new ArrayList<>();
        try (Connection connection = ConnectionProvider.get();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ExchangeRateSqlQueries.GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exchangeRates.add(buildExchangeRateEntity(resultSet));
            }
        } catch (Exception e) {
            throw ExceptionConverter.toCurrencyExchangerException(e);
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
