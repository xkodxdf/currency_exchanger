package com.xkodxdf.app.controller;

import java.math.BigDecimal;

public final class RequestDataVerifier {

    private static final int urlSlashLength = 1;
    private static final int currencyCodeLength = 3;

    private RequestDataVerifier() {
    }

    public static boolean isDataPresent(String... data) {
        for (String s : data) {
            if (s == null || s.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUrlCurrencyCodeLegal(String urlCode) {
        if (isDataPresent(urlCode)) {
            return urlCode.length() == urlSlashLength + currencyCodeLength;
        }
        return false;
    }

    public static boolean isUrlCurrencyCodePairLegal(String urlCodePair) {
        if (isDataPresent(urlCodePair)) {
            return urlCodePair.length() == currencyCodeLength * 2 + urlSlashLength;
        }
        return false;
    }

    public static boolean canBeConvertedToBigDecimal(String rate) {
        try {
            new BigDecimal(rate);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
