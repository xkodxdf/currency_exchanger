package com.xkodxdf.app.model.service;

import com.xkodxdf.app.model.dao.ExchangeRateDaoImpl;
import com.xkodxdf.app.model.dto.ExchangeRateDto;
import com.xkodxdf.app.model.entity.ExchangeRateEntity;

import java.math.BigDecimal;
import java.util.List;

public final class ExchangeRateService {

    private static ExchangeRateService INSTANCE;

    private final ExchangeRateDaoImpl exchangeRateDao = ExchangeRateDaoImpl.getInstance();

    private ExchangeRateService() {
    }

    public static ExchangeRateService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ExchangeRateService();
        }
        return INSTANCE;
    }

    public ExchangeRateEntity save(String codes, BigDecimal rate) {
        return exchangeRateDao.save(new ExchangeRateDto(getBaseCurrencyCode(codes), getTargetCurrencyCode(codes), rate));
    }

    public ExchangeRateEntity get(String codes) {
        return exchangeRateDao.get(new ExchangeRateDto(getBaseCurrencyCode(codes), getTargetCurrencyCode(codes), null));
    }

    public ExchangeRateEntity update(String codes, BigDecimal newRate) {
        return exchangeRateDao.update(new ExchangeRateDto(getBaseCurrencyCode(codes), getTargetCurrencyCode(codes), newRate));
    }

    public List<ExchangeRateEntity> getAll() {
        return exchangeRateDao.getAll();
    }

    private String getBaseCurrencyCode(String codePair) {
        return codePair.substring(0, 3);
    }

    private String getTargetCurrencyCode(String codePair) {
        return codePair.substring(3);
    }
}

