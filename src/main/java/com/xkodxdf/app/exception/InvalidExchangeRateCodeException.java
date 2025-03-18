package com.xkodxdf.app.exception;

public class InvalidExchangeRateCodeException extends CurrencyExchangerException {

    public InvalidExchangeRateCodeException() {
    }

    public InvalidExchangeRateCodeException(String message) {
        super(message);
    }

    public InvalidExchangeRateCodeException(Exception e) {
        super(e);
    }
}
