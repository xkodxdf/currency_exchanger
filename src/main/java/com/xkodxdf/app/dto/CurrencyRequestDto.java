package com.xkodxdf.app.dto;

public record CurrencyRequestDto(
        String name,
        String code,
        String sign) {

    public CurrencyRequestDto(String code) {
        this(null, code, null);
    }
}
