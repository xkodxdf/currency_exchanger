package com.xkodxdf.app.entity;

import java.math.BigDecimal;
import java.util.Objects;

public record ExchangeRateEntity(
        Long id,
        CurrencyEntity baseCurrency,
        CurrencyEntity targetCurrency,
        BigDecimal rate) {

    public ExchangeRateEntity {
        Objects.requireNonNull(id);
        Objects.requireNonNull(baseCurrency);
        Objects.requireNonNull(targetCurrency);
        Objects.requireNonNull(rate);
    }
}