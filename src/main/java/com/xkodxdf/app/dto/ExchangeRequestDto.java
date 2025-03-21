package com.xkodxdf.app.dto;

public record ExchangeRequestDto(
        ExchangeRateRequestDto exchangeRateRequestDto,
        String amount) {
}
