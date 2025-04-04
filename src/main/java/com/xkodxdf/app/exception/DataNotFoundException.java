package com.xkodxdf.app.exception;

import com.xkodxdf.app.ErrorMessage;

public class DataNotFoundException extends CurrencyExchangerException {

    public DataNotFoundException(Throwable t) {
        super(ErrorMessage.NOT_FOUND_ERR, t);
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}
