package com.xkodxdf.app.model.dto;

import java.math.BigDecimal;

public record ExchangeRequestDto(
        ExchangeRateRequestDto exchangeRateRequestDto,
        BigDecimal amount) {
}
