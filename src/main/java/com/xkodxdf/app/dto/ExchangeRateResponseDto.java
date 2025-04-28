package com.xkodxdf.app.dto;

import com.xkodxdf.app.entity.CurrencyEntity;
import com.xkodxdf.app.entity.ExchangeRateEntity;

import java.math.BigDecimal;

public record ExchangeRateResponseDto(
        Long id,
        CurrencyEntity baseCurrency,
        CurrencyEntity targetCurrency,
        BigDecimal rate) {

    public ExchangeRateResponseDto(ExchangeRateEntity exchangeRateEntity) {
        this(exchangeRateEntity.id(),
                exchangeRateEntity.baseCurrency(),
                exchangeRateEntity.targetCurrency(),
                exchangeRateEntity.rate()
        );
    }
}
