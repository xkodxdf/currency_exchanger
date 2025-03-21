package com.xkodxdf.app.exception;

import com.xkodxdf.app.ErrorMessage;

public class InvalidCurrencyCodeException extends InvalidRequestDataException {

    public InvalidCurrencyCodeException() {
        super(ErrorMessage.INVALID_CURRENCY_CODE);
    }

    public InvalidCurrencyCodeException(Throwable t) {
        super(ErrorMessage.INVALID_CURRENCY_CODE, t);
    }

    public InvalidCurrencyCodeException(String message) {
        super(message);
    }

    public InvalidCurrencyCodeException(String message, Throwable t) {
        super(message, t);
    }
}
