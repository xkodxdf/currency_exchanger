package com.xkodxdf.app.model.service;

import com.xkodxdf.app.model.dao.CurrencyDaoImpl;
import com.xkodxdf.app.model.dto.CurrencyRequestDto;
import com.xkodxdf.app.model.dto.CurrencyResponseDto;
import com.xkodxdf.app.model.entity.CurrencyEntity;

import java.util.List;

public final class CurrencyService {

    private static CurrencyService INSTANCE;

    private final CurrencyDaoImpl currencyDao = CurrencyDaoImpl.getInstance();

    private CurrencyService() {
    }

    public static CurrencyService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CurrencyService();
        }
        return INSTANCE;
    }

    public CurrencyResponseDto save(CurrencyRequestDto requestDto) {
        CurrencyEntity currencyEntity = currencyDao.save(requestDto);
        return new CurrencyResponseDto(currencyEntity);
    }

    public CurrencyResponseDto get(CurrencyRequestDto requestDto) {
        CurrencyEntity currencyEntity = currencyDao.get(requestDto);
        return new CurrencyResponseDto(currencyEntity);
    }

    public CurrencyResponseDto delete(CurrencyRequestDto requestDto) {
        CurrencyEntity currencyEntity = currencyDao.delete(requestDto);
        return new CurrencyResponseDto(currencyEntity);
    }

    public List<CurrencyResponseDto> getAll() {
        return currencyDao.getAll().stream()
                .map(CurrencyResponseDto::new)
                .toList();
    }
}
