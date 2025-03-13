package com.xkodxdf.app.model.dto;

import com.xkodxdf.app.model.entity.CurrencyEntity;
import com.xkodxdf.app.model.entity.ExchangeEntity;

import java.math.BigDecimal;

public record ExchangeResponseDto(
        CurrencyEntity baseCurrency,
        CurrencyEntity targetCurrency,
        BigDecimal rate,
        BigDecimal amount,
        BigDecimal convertedAmount) {

    public ExchangeResponseDto(ExchangeEntity exchangeEntity) {
        this(exchangeEntity.baseCurrency(),
                exchangeEntity.targetCurrency(),
                exchangeEntity.rate(),
                exchangeEntity.amount(),
                exchangeEntity.convertedAmount()
        );
    }
}
