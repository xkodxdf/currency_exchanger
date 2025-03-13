package com.xkodxdf.app.model.service;

import com.xkodxdf.app.model.dao.ExchangeRateDaoImpl;
import com.xkodxdf.app.model.dto.ExchangeRequestDto;
import com.xkodxdf.app.model.dto.ExchangeResponseDto;
import com.xkodxdf.app.model.entity.ExchangeEntity;
import com.xkodxdf.app.model.entity.ExchangeRateEntity;

public final class ExchangeService {

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

    public ExchangeResponseDto getExchangeEntity(ExchangeRequestDto requestDto) {
        ExchangeRateEntity exchangeRate = exchangeRateDao.get(requestDto.exchangeRateRequestDto());
        ExchangeEntity exchangeEntity = new ExchangeEntity(exchangeRate, requestDto.amount());
        return new ExchangeResponseDto(exchangeEntity);
    }
}
