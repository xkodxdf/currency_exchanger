package com.xkodxdf.app.dto;

public record ExchangeRateRequestDto(
        String baseCurrencyCode,
        String targetCurrencyCode,
        String rate) {

    public ExchangeRateRequestDto(String baseCurrencyCode, String targetCurrencyCode) {
        this(baseCurrencyCode, targetCurrencyCode, null);
    }
}
