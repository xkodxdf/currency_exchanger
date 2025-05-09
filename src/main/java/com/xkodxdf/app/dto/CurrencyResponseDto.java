package com.xkodxdf.app.dto;

import com.xkodxdf.app.entity.CurrencyEntity;

public record CurrencyResponseDto(
        Long id,
        String name,
        String code,
        String sign) {

    public CurrencyResponseDto(CurrencyEntity currencyEntity) {
        this(currencyEntity.id(),
                currencyEntity.name(),
                currencyEntity.code(),
                currencyEntity.sign()
        );
    }
}
