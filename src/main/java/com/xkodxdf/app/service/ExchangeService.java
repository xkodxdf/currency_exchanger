package com.xkodxdf.app.service;

import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRequestDto;
import com.xkodxdf.app.dto.ExchangeResponseDto;
import com.xkodxdf.app.model.dao.interfaces.ExchangeRateDao;
import com.xkodxdf.app.model.entity.ExchangeEntity;
import com.xkodxdf.app.model.entity.ExchangeRateEntity;

import java.math.BigDecimal;

public class ExchangeService extends BaseService {

    private final ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao;

    public ExchangeService(ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    public ExchangeResponseDto getExchangeResponseDto(ExchangeRequestDto requestDtoToExchange) {
        requestDtoValidator.validateExchangeRequestDto(requestDtoToExchange);
        ExchangeRateEntity exchangeRate = findExchangeRate(requestDtoToExchange.exchangeRateRequestDto());
        BigDecimal amountToExchange = new BigDecimal(requestDtoToExchange.amount());
        ExchangeEntity exchangeEntity = new ExchangeEntity(exchangeRate, amountToExchange);
        return new ExchangeResponseDto(exchangeEntity);
    }
}
