package com.xkodxdf.app.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class ExchangeRateEntity {

    private final Long id;
    private final CurrencyEntity baseCurrency;
    private final CurrencyEntity targetCurrency;
    private final BigDecimal rate;

    public ExchangeRateEntity(Long id, CurrencyEntity baseCurrency, CurrencyEntity targetCurrency, BigDecimal rate) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(baseCurrency);
        Objects.requireNonNull(targetCurrency);
        Objects.requireNonNull(rate);
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public CurrencyEntity getBaseCurrency() {
        return baseCurrency;
    }

    public CurrencyEntity getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "ExchangeRateEntity{" +
               "id=" + id +
               ", baseCurrency=" + baseCurrency +
               ", targetCurrency=" + targetCurrency +
               ", rate=" + rate +
               '}';
    }
}