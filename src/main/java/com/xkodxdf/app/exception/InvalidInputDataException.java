package com.xkodxdf.app.exception;

public class InvalidInputDataException extends CurrencyExchangerException {

    public InvalidInputDataException() {
    }

    public InvalidInputDataException(String message) {
        super(message);
    }

    public InvalidInputDataException(Exception e) {
        super(e);
    }
}
