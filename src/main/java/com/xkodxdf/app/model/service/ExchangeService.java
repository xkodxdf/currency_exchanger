package com.xkodxdf.app.model.service;

import com.xkodxdf.app.model.dao.interfaces.ExchangeRateDao;
import com.xkodxdf.app.model.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.model.dto.ExchangeRequestDto;
import com.xkodxdf.app.model.dto.ExchangeResponseDto;
import com.xkodxdf.app.model.entity.ExchangeEntity;
import com.xkodxdf.app.model.entity.ExchangeRateEntity;

public class ExchangeService {

    private final ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao;

    public ExchangeService(ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    public ExchangeResponseDto getExchangeEntity(ExchangeRequestDto requestDto) {
        ExchangeRateEntity exchangeRate = exchangeRateDao.get(requestDto.exchangeRateRequestDto());
        ExchangeEntity exchangeEntity = new ExchangeEntity(exchangeRate, requestDto.amount());
        return new ExchangeResponseDto(exchangeEntity);
    }
}
