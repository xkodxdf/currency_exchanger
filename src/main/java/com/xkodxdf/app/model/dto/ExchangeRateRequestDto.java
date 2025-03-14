package com.xkodxdf.app.model.dto;

import java.math.BigDecimal;

public record ExchangeRateRequestDto(
        String baseCurrencyCode,
        String targetCurrencyCode,
        BigDecimal rate) {

    public ExchangeRateRequestDto(String baseCurrencyCode, String targetCurrencyCode) {
        this(baseCurrencyCode, targetCurrencyCode, null);
    }
}
