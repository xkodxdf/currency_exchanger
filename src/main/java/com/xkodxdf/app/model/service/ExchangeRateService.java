package com.xkodxdf.app.model.service;

import com.xkodxdf.app.model.dao.interfaces.ExchangeRateDao;
import com.xkodxdf.app.model.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.model.dto.ExchangeRateResponseDto;
import com.xkodxdf.app.model.entity.ExchangeRateEntity;

import java.util.List;

public class ExchangeRateService {

    private final ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao;

    public ExchangeRateService(ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    public ExchangeRateResponseDto save(ExchangeRateRequestDto requestDto) {
        ExchangeRateEntity exchangeRateEntity = exchangeRateDao.save(requestDto);
        return new ExchangeRateResponseDto(exchangeRateEntity);
    }

    public ExchangeRateResponseDto get(ExchangeRateRequestDto requestDto) {
        ExchangeRateEntity exchangeRateEntity = exchangeRateDao.get(requestDto);
        return new ExchangeRateResponseDto(exchangeRateEntity);
    }

    public ExchangeRateResponseDto update(ExchangeRateRequestDto requestDto) {
        ExchangeRateEntity exchangeRateEntity = exchangeRateDao.update(requestDto);
        return new ExchangeRateResponseDto(exchangeRateEntity);
    }

    public List<ExchangeRateResponseDto> getAll() {
        return exchangeRateDao.getAll().stream()
                .map(ExchangeRateResponseDto::new)
                .toList();
    }

}

