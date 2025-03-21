package com.xkodxdf.app.exception;

import com.xkodxdf.app.ErrorMessage;

public class InvalidExchangeRateCodeException extends InvalidRequestDataException {

    public InvalidExchangeRateCodeException() {
        super(ErrorMessage.INVALID_EXCHANGE_RATE_CODE_ERR);
    }

    public InvalidExchangeRateCodeException(Throwable t) {
        super(ErrorMessage.INVALID_EXCHANGE_RATE_CODE_ERR, t);
    }

    public InvalidExchangeRateCodeException(String message) {
        super(message);
    }

    public InvalidExchangeRateCodeException(String message, Throwable t) {
        super(message, t);
    }
}
