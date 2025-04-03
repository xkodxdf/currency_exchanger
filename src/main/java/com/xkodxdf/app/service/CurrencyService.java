package com.xkodxdf.app.service;

import com.xkodxdf.app.model.dao.interfaces.CurrencyDao;
import com.xkodxdf.app.dto.CurrencyRequestDto;
import com.xkodxdf.app.dto.CurrencyResponseDto;
import com.xkodxdf.app.model.entity.CurrencyEntity;

import java.util.List;

public class CurrencyService {

    private final CurrencyDao<CurrencyRequestDto, CurrencyEntity> currencyDao;
    private final RequestDtoValidator requestDtoValidator;

    public CurrencyService(CurrencyDao<CurrencyRequestDto, CurrencyEntity> currencyDao,
                           RequestDtoValidator requestDtoValidator) {
        this.currencyDao = currencyDao;
        this.requestDtoValidator = requestDtoValidator;
    }

    public CurrencyResponseDto save(CurrencyRequestDto requestDtoToSave) {
        requestDtoValidator.validateCurrencyRequestDtoForSaving(requestDtoToSave);
        CurrencyEntity savedCurrency = currencyDao.save(requestDtoToSave);
        return new CurrencyResponseDto(savedCurrency);
    }

    public CurrencyResponseDto get(CurrencyRequestDto requestDtoToReceive) {
        requestDtoValidator.validateCurrencyRequestDtoForReceivingOrDeleting(requestDtoToReceive);
        CurrencyEntity receivedCurrency = currencyDao.get(requestDtoToReceive);
        return new CurrencyResponseDto(receivedCurrency);
    }

    public CurrencyResponseDto delete(CurrencyRequestDto requestDtoToDelete) {
        requestDtoValidator.validateCurrencyRequestDtoForReceivingOrDeleting(requestDtoToDelete);
        CurrencyEntity deletedCurrency = currencyDao.delete(requestDtoToDelete);
        return new CurrencyResponseDto(deletedCurrency);
    }

    public List<CurrencyResponseDto> getAll() {
        return currencyDao.getAll().stream()
                .map(CurrencyResponseDto::new)
                .toList();
    }
}
