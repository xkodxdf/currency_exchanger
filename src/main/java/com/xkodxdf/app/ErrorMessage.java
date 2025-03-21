package com.xkodxdf.app;

public final class ErrorMessage {

    private ErrorMessage() {
    }

    public static final String UNEXPECTED_ERR = "An unexpected error occurred";
    public static final String REQUEST_DATA_ERR = "Incorrect data in fields or url";
    public static final String DUPLICATION_ERR = "This data has already been added earlier";
    public static final String NOT_FOUND_ERR = "Unable to find data";
    public static final String REQUIRED_PARAMS_ERR = "All field must be filled with correct data";
    public static final String INVALID_CURRENCY_CODE = "Currency code must consist of three latin letters (ex. USD)";
    public static final String INVALID_NUMERIC_STRING_LENGTH = "Maximum number of characters for exchange rate " +
                                                               "or amount to exchange 17";
    public static final String INVALID_EXCHANGE_RATE_CODE_ERR = "Exchange rate code should consist of two currency codes," +
                                                                " which in turn consist of three Latin letters (ex. USDEUR)";
}
