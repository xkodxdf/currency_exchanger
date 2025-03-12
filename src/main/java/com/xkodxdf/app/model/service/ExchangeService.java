package com.xkodxdf.app.model.service;

import com.xkodxdf.app.model.dao.ExchangeRateDaoImpl;
import com.xkodxdf.app.model.dto.ExchangeRateDto;
import com.xkodxdf.app.model.entity.ExchangeEntity;
import com.xkodxdf.app.model.entity.ExchangeRateEntity;

import java.math.BigDecimal;

public class ExchangeService {

    private static ExchangeService INSTANCE;

    private final ExchangeRateDaoImpl exchangeRateDao = ExchangeRateDaoImpl.getInstance();

    private ExchangeService() {
    }

    public static ExchangeService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ExchangeService();
        }
        return INSTANCE;
    }

    public ExchangeEntity getExchangeEntity(String codes, String amount) {
        ExchangeRateEntity exchangeRate = exchangeRateDao.get(new ExchangeRateDto(
                getBaseCurrencyCode(codes), getTargetCurrencyCode(codes), null));
        return new ExchangeEntity(exchangeRate, new BigDecimal(amount));
    }

    private String getBaseCurrencyCode(String codePair) {
        return codePair.substring(0, 3);
    }

    private String getTargetCurrencyCode(String codePair) {
        return codePair.substring(3);
    }
}
