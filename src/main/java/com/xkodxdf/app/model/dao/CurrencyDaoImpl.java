package com.xkodxdf.app.model.dao;


import com.xkodxdf.app.dto.CurrencyRequestDto;
import com.xkodxdf.app.model.dao.interfaces.CurrencyDao;
import com.xkodxdf.app.model.entity.CurrencyEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDaoImpl implements CurrencyDao<CurrencyRequestDto, CurrencyEntity> {

    private static final String ID_COLUMN_LABEL = "id";
    private static final String SIGN_COLUMN_LABEL = "sign";
    private static final String CODE_COLUMN_LABEL = "code";
    private static final String NAME_COLUMN_LABEL = "full_name";

    private final SqlHelper sqlHelper;

    public CurrencyDaoImpl(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public CurrencyEntity save(CurrencyRequestDto requestDto) {
        return sqlHelper.executeStatement(CurrencySqlQueries.SAVE, Statement.RETURN_GENERATED_KEYS,
                preparedStatement -> {
                    preparedStatement.setString(1, requestDto.sign());
                    preparedStatement.setString(2, requestDto.code());
                    preparedStatement.setString(3, requestDto.name());
                    preparedStatement.executeUpdate();
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    generatedKeys.next();
                    long currencyId = generatedKeys.getLong(ID_COLUMN_LABEL);
                    return new CurrencyEntity(currencyId, requestDto);
                });
    }

    @Override
    public CurrencyEntity get(CurrencyRequestDto requestDto) {
        return getCurrencyEntity(requestDto, CurrencySqlQueries.GET);
    }

    @Override
    public CurrencyEntity delete(CurrencyRequestDto requestDto) {
        return getCurrencyEntity(requestDto, CurrencySqlQueries.DELETE);
    }

    @Override
    public List<CurrencyEntity> getAll() {
        return sqlHelper.executeStatement(CurrencySqlQueries.GET_ALL, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CurrencyEntity> currencies = new ArrayList<>();
            while (resultSet.next()) {
                currencies.add(buildCurrency(resultSet));
            }
            return currencies;
        });
    }

    private CurrencyEntity getCurrencyEntity(CurrencyRequestDto requestDto, String sql) {
        return sqlHelper.executeStatement(sql, preparedStatement -> {
            preparedStatement.setString(1, requestDto.code());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildCurrency(resultSet);
        });
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
