package com.xkodxdf.app.service;

import com.xkodxdf.app.model.dao.interfaces.ExchangeRateDao;
import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRateResponseDto;
import com.xkodxdf.app.model.entity.ExchangeRateEntity;

import java.util.List;

public class ExchangeRateService extends BaseService {

    private final ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao;

    public ExchangeRateService(ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
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

