package com.xkodxdf.app.exception;

public class NotAllRequiredParametersPassedException extends CurrencyExchangerException {

    public NotAllRequiredParametersPassedException() {
    }

    public NotAllRequiredParametersPassedException(String message) {
        super(message);
    }

    public NotAllRequiredParametersPassedException(Exception e) {
        super(e);
    }
}
