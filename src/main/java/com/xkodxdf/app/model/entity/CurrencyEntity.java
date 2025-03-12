package com.xkodxdf.app.model.entity;

import java.util.Objects;

public class CurrencyEntity {

    private Long id;
    private final String name;
    private final String code;
    private final String sign;


    public CurrencyEntity(String name, String code, String sign) {
        this(null, name, code, sign);
    }

    public CurrencyEntity(Long id, String name, String code, String sign) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(code);
        Objects.requireNonNull(sign);
        this.id = id;
        this.name = name;
        this.code = code;
        this.sign = sign;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return "CurrencyEntity{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", code='" + code + '\'' +
               ", sign='" + sign + '\'' +
               '}';
    }
}