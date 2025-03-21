package com.xkodxdf.app.exception;

import com.xkodxdf.app.ErrorMessage;

public class InvalidRequestDataException extends CurrencyExchangerException {

    public InvalidRequestDataException() {
        super(ErrorMessage.REQUEST_DATA_ERR);
    }

    public InvalidRequestDataException(Throwable t) {
        super(ErrorMessage.REQUEST_DATA_ERR, t);
    }

    public InvalidRequestDataException(String message) {
        super(message);
    }

    public InvalidRequestDataException(String message, Throwable t) {
        super(message, t);
    }
}
