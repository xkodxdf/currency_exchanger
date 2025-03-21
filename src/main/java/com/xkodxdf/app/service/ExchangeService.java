package com.xkodxdf.app.service;

import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRequestDto;
import com.xkodxdf.app.dto.ExchangeResponseDto;
import com.xkodxdf.app.model.dao.interfaces.ExchangeRateDao;
import com.xkodxdf.app.model.entity.ExchangeEntity;
import com.xkodxdf.app.model.entity.ExchangeRateEntity;

import java.math.BigDecimal;

public class ExchangeService {

    private final ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao;

    public ExchangeService(ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    public ExchangeResponseDto getExchangeResponseDto(ExchangeRequestDto exchangeRequestDto) {
        ExchangeRateEntity exchangeRate = exchangeRateDao.get(exchangeRequestDto.exchangeRateRequestDto());
        BigDecimal amountToExchange = new BigDecimal(exchangeRequestDto.amount());
        ExchangeEntity exchangeEntity = new ExchangeEntity(exchangeRate, amountToExchange);
        return new ExchangeResponseDto(exchangeEntity);
    }
}
