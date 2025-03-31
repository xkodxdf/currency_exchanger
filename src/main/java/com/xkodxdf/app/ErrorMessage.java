package com.xkodxdf.app;

public final class ErrorMessage {

    private ErrorMessage() {
    }

    public static final String UNEXPECTED_ERR = "An unexpected error occurred";
    public static final String REQUEST_DATA_ERR = "Incorrect data in fields or url";
    public static final String DUPLICATION_ERR = "This data has already been added earlier";
    public static final String NOT_FOUND_ERR = "Unable to find data";
    public static final String REQUIRED_PARAMS_ERR = "All field must be filled with correct data";
    public static final String AMOUNT_TO_CONVERT_TOO_SMALL = "Amount to convert must be greater or equals 0.01";
    public static final String INVALID_CURRENCY_CODE = "Currency code must consist of three latin letters (ex. USD)";
    public static final String INVALID_CURRENCY_SIGN = "Currency sign length must be 1-3 characters (ex. $, A$, CN$)";
    public static final String INVALID_CURRENCY_NAME = "Currency name may contain latin letters and spaces, " +
                                                       "maximum length 48 characters";
    public static final String EXCHANGE_RATE_OUT_OF_BOUND = "The exchange rate should be between 0.000001 " +
                                                            "and 999999.999999 inclusive";
    public static final String BIG_DECIMAL_CONVERSION_ERR = "Incorrect data. Enter an integer or decimal number with a dot " +
                                                            "(ex. 112, 44.55, 0.011)";
    public static final String INVALID_NUMERIC_STRING_LENGTH = "A maximum of 12 digits in a number is allowed " +
                                                               "for the exchange rate";
    public static final String INVALID_EXCHANGE_RATE_CODE = "Exchange rate code should consist of two currency codes," +
                                                            " which in turn consist of three Latin letters (ex. USDEUR)";
}
