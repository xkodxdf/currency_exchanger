package com.xkodxdf.app.exception;

import com.xkodxdf.app.ErrorMessage;

public class DataAlreadyExistException extends CurrencyExchangerException {

    public DataAlreadyExistException() {
        super(ErrorMessage.DUPLICATION_ERR);
    }

    public DataAlreadyExistException(Throwable t) {
        super(ErrorMessage.DUPLICATION_ERR, t);
    }

    public DataAlreadyExistException(String message) {
        super(message);
    }

    public DataAlreadyExistException(String message, Throwable t) {
        super(message, t);
    }
}
