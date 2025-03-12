package com.xkodxdf.app.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

public final class ExchangeEntity {

    private final CurrencyEntity baseCurrency;
    private final CurrencyEntity targetCurrency;
    private final BigDecimal rate;
    private final BigDecimal amount;
    private final BigDecimal convertedAmount;

    public ExchangeEntity(ExchangeRateEntity exchangeRate, BigDecimal amount) {
        Objects.requireNonNull(exchangeRate);
        Objects.requireNonNull(amount);
        this.baseCurrency = exchangeRate.getBaseCurrency();
        this.targetCurrency = exchangeRate.getTargetCurrency();
        this.rate = exchangeRate.getRate();
        this.amount = amount;
        this.convertedAmount = exchangeCurrency(rate, amount);
    }

    private BigDecimal exchangeCurrency(BigDecimal rate, BigDecimal amount) {
        return rate.multiply(amount);
    }

    @Override
    public String toString() {
        return "ExchangeEntity[" +
               "baseCurrency=" + baseCurrency + ", " +
               "targetCurrency=" + targetCurrency + ", " +
               "rate=" + rate + ", " +
               "amount=" + amount + ", " +
               "convertedAmount=" + convertedAmount + ']';
    }

}
