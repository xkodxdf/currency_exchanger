package com.xkodxdf.app.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        int numsAfterDecimal = 12;
        return rate.multiply(amount).setScale(numsAfterDecimal, RoundingMode.HALF_EVEN);
    }
}
