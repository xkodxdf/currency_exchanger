package com.xkodxdf.app.service;

import com.xkodxdf.app.model.dao.interfaces.CurrencyDao;
import com.xkodxdf.app.dto.CurrencyRequestDto;
import com.xkodxdf.app.dto.CurrencyResponseDto;
import com.xkodxdf.app.model.entity.CurrencyEntity;

import java.util.List;

public class CurrencyService {

    private final CurrencyDao<CurrencyRequestDto, CurrencyEntity> currencyDao;

    public CurrencyService(CurrencyDao<CurrencyRequestDto, CurrencyEntity> currencyDao) {
        this.currencyDao = currencyDao;
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
