package com.xkodxdf.app.exception;

import com.xkodxdf.app.ErrorMessage;

public class DataAlreadyExistException extends CurrencyExchangerException {

    public DataAlreadyExistException(Throwable t) {
        super(ErrorMessage.DUPLICATION_ERR, t);
    }
}
