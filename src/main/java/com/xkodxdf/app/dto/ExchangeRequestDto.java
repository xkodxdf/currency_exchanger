package com.xkodxdf.app.dto;

import java.math.BigDecimal;

public record ExchangeRequestDto(
        ExchangeRateRequestDto exchangeRateRequestDto,
        BigDecimal amount) {
}
