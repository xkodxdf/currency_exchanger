package com.xkodxdf.app.service;

import com.xkodxdf.app.dao.ExchangeRateDao;
import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRateResponseDto;
import com.xkodxdf.app.entity.ExchangeRateEntity;

import java.util.List;

public class ExchangeRateService {

    private final ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao;
    private final RequestDtoValidator requestDtoValidator;

    public ExchangeRateService(ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao,
                               RequestDtoValidator requestDtoValidator) {
        this.exchangeRateDao = exchangeRateDao;
        this.requestDtoValidator = requestDtoValidator;
    }

    public ExchangeRateResponseDto save(ExchangeRateRequestDto requestDtoToSave) {
        requestDtoValidator.validateExchangeRateRequestDtoForSavingOrUpdating(requestDtoToSave);
        ExchangeRateEntity savedExchangeRate = exchangeRateDao.save(requestDtoToSave);
        return new ExchangeRateResponseDto(savedExchangeRate);
    }

    public ExchangeRateResponseDto get(ExchangeRateRequestDto requestDtoToReceive) {
        requestDtoValidator.validateExchangeRateRequestDtoForReceiving(requestDtoToReceive);
        ExchangeRateEntity receivedExchangeRate = exchangeRateDao.get(requestDtoToReceive);
        return new ExchangeRateResponseDto(receivedExchangeRate);
    }

    public ExchangeRateResponseDto update(ExchangeRateRequestDto requestDtoToUpdate) {
        requestDtoValidator.validateExchangeRateRequestDtoForSavingOrUpdating(requestDtoToUpdate);
        ExchangeRateEntity updatedExchangeRate = exchangeRateDao.update(requestDtoToUpdate);
        return new ExchangeRateResponseDto(updatedExchangeRate);
    }

    public List<ExchangeRateResponseDto> getAll() {
        return exchangeRateDao.getAll().stream()
                .map(ExchangeRateResponseDto::new)
                .toList();
    }
}

