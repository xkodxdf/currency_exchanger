package com.xkodxdf.app.exception;

import com.xkodxdf.app.ErrorMessage;

public class CurrencyExchangerException extends RuntimeException {

    public CurrencyExchangerException() {
        super(ErrorMessage.UNEXPECTED_ERR);
    }

    public CurrencyExchangerException(Throwable t) {
        super(ErrorMessage.UNEXPECTED_ERR, t);
    }

    public CurrencyExchangerException(String message) {
        super(message);
    }

    public CurrencyExchangerException(String message, Throwable t) {
        super(message, t);
    }
}
