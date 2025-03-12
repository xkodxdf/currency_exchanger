package com.xkodxdf.app.exception;

public class CurrencyExchangerException extends RuntimeException {

    public CurrencyExchangerException() {
    }

    public CurrencyExchangerException(String message) {
        super(message);
    }

    public CurrencyExchangerException(Exception e) {
        super(e);
    }
}
