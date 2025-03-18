package com.xkodxdf.app.exception;

public class DataNotFoundExcepton extends CurrencyExchangerException {

    public DataNotFoundExcepton() {
    }

    public DataNotFoundExcepton(String message) {
        super(message);
    }

    public DataNotFoundExcepton(Exception e) {
        super(e);
    }
}
