package com.xkodxdf.app.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

public record ExchangeEntity(
        CurrencyEntity baseCurrency,
        CurrencyEntity targetCurrency,
        BigDecimal rate,
        BigDecimal amount,
        BigDecimal convertedAmount) {

    public ExchangeEntity(
            CurrencyEntity baseCurrency,
            CurrencyEntity targetCurrency,
            BigDecimal rate,
            BigDecimal amount) {
        this(baseCurrency,
                targetCurrency,
                rate,
                amount,
                exchangeCurrency(rate, amount)
        );
    }

    public ExchangeEntity(ExchangeRateEntity exchangeRate, BigDecimal amount) {
        this(Objects.requireNonNull(exchangeRate).baseCurrency(),
                Objects.requireNonNull(exchangeRate).targetCurrency(),
                Objects.requireNonNull(exchangeRate).rate(),
                Objects.requireNonNull(amount)
        );
    }

    private static BigDecimal exchangeCurrency(BigDecimal rate, BigDecimal amount) {
        return rate.multiply(amount);
    }
}
