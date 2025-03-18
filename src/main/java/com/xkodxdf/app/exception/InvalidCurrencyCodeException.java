package com.xkodxdf.app.exception;

public class InvalidCurrencyCodeException extends CurrencyExchangerException {

    public InvalidCurrencyCodeException() {
    }

    public InvalidCurrencyCodeException(String message) {
        super(message);
    }

    public InvalidCurrencyCodeException(Exception e) {
        super(e);
    }
}
