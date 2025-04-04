package com.xkodxdf.app.service;

import com.xkodxdf.app.ErrorMessage;
import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRequestDto;
import com.xkodxdf.app.dto.ExchangeResponseDto;
import com.xkodxdf.app.exception.DataNotFoundException;
import com.xkodxdf.app.model.dao.interfaces.ExchangeRateDao;
import com.xkodxdf.app.model.entity.ExchangeEntity;
import com.xkodxdf.app.model.entity.ExchangeRateEntity;

import java.math.BigDecimal;

public class ExchangeService {

    private final ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao;
    private final RequestDtoValidator requestDtoValidator;

    public ExchangeService(ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao,
                           RequestDtoValidator requestDtoValidator) {
        this.exchangeRateDao = exchangeRateDao;
        this.requestDtoValidator = requestDtoValidator;
    }

    public ExchangeResponseDto getExchangeResponseDto(ExchangeRequestDto requestDtoToExchange) {
        requestDtoValidator.validateExchangeRequestDto(requestDtoToExchange);
        ExchangeRateEntity exchangeRate = findExchangeRate(requestDtoToExchange.exchangeRateRequestDto());
        BigDecimal amountToExchange = new BigDecimal(requestDtoToExchange.amount());
        ExchangeEntity exchangeEntity = new ExchangeEntity(exchangeRate, amountToExchange);
        return new ExchangeResponseDto(exchangeEntity);
    }

    private ExchangeRateEntity findExchangeRate(ExchangeRateRequestDto requestDto) {
        return exchangeRateDao.find(requestDto)
                .orElseThrow(() -> new DataNotFoundException(ErrorMessage.NOT_FOUND_ERR));
    }
}
