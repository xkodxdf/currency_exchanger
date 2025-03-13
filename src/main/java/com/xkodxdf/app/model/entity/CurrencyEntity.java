package com.xkodxdf.app.model.entity;

import com.xkodxdf.app.model.dto.CurrencyRequestDto;

import java.util.Objects;

public record CurrencyEntity(
        Long id,
        String name,
        String code,
        String sign) {


    public CurrencyEntity {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(code);
        Objects.requireNonNull(sign);
    }

    public CurrencyEntity(Long id, CurrencyRequestDto currencyRequestDto) {
        this(id, currencyRequestDto.name(),
                currencyRequestDto.code(),
                currencyRequestDto.sign()
        );
    }
}