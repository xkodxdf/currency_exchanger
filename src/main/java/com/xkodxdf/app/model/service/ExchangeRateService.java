package com.xkodxdf.app.model.service;

import com.xkodxdf.app.model.dao.ExchangeRateDaoImpl;
import com.xkodxdf.app.model.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.model.dto.ExchangeRateResponseDto;
import com.xkodxdf.app.model.entity.ExchangeRateEntity;

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

