package com.xkodxdf.app.exception;

public class DataAlreadyExistException extends CurrencyExchangerException {

    public DataAlreadyExistException() {
    }

    public DataAlreadyExistException(String message) {
        super(message);
    }

    public DataAlreadyExistException(Exception e) {
        super(e);
    }
}
