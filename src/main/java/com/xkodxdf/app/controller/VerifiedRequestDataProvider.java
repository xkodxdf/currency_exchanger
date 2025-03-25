package com.xkodxdf.app.controller;

import com.xkodxdf.app.ErrorMessage;
import com.xkodxdf.app.dto.CurrencyRequestDto;
import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRequestDto;
import com.xkodxdf.app.exception.CurrencyExchangerException;
import com.xkodxdf.app.exception.InvalidRequestDataException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

public class VerifiedRequestDataProvider {

    public static final int CURRENCY_CODE_VALID_LENGTH = 3;
    public static final int CURRENCY_NAME_MAX_LENGTH = 48;
    public static final int CURRENCY_SIGN_MAX_LENGTH = 3;
    public static final int NUMERIC_STRING_MAX_LENGTH = 16;
    public static final String CURRENCY_CODE_PARAMETER = "code";
    public static final String CURRENCY_NAME_PARAMETER = "name";
    public static final String CURRENCY_SIGN_PARAMETER = "sign";
    public static final String EXCHANGE_RATE_BASE_CURRENCY_PARAMETER = "baseCurrencyCode";
    public static final String EXCHANGE_RATE_TARGET_CURRENCY_PARAMETER = "targetCurrencyCode";
    public static final String EXCHANGE_RATE_RATE_PARAMETER = "rate";
    public static final String UPDATE_RATE_PARAMETER = "rate=";


    public void verifyDataPresence(String... data) {
        for (String s : data) {
            if (s == null || s.isEmpty()) {
                throw new InvalidRequestDataException(ErrorMessage.REQUIRED_PARAMS_ERR);
            }
        }
    }

    public CurrencyRequestDto getCurrencyRequestDtoForReceivingOrDeleting(String requestCode) {
        return new CurrencyRequestDto(getProcessedCurrencyCode(requestCode));
    }

    public CurrencyRequestDto getCurrencyRequestDtoForSaving(HttpServletRequest req) {
        String name = req.getParameter(CURRENCY_NAME_PARAMETER);
        String code = req.getParameter(CURRENCY_CODE_PARAMETER);
        String sign = req.getParameter(CURRENCY_SIGN_PARAMETER);
        return new CurrencyRequestDto(
                getProcessedCurrencyName(name),
                getProcessedCurrencyCode(code),
                getProcessedCurrencySign(sign)
        );
    }


    public ExchangeRateRequestDto getExchangeRateRequestDtoForReceiving(String codePair) {
        String processedCodePair = getProcessedCurrencyCodePair(codePair);
        return new ExchangeRateRequestDto(
                getBaseCurrencyCode(processedCodePair),
                getTargetCurrencyCode(processedCodePair)
        );
    }

    public ExchangeRateRequestDto getExchangeRateRequestDtoForSaving(HttpServletRequest req) {
        String baseCurrencyCode = req.getParameter(EXCHANGE_RATE_BASE_CURRENCY_PARAMETER);
        String targetCurrencyCode = req.getParameter(EXCHANGE_RATE_TARGET_CURRENCY_PARAMETER);
        String rate = req.getParameter(EXCHANGE_RATE_RATE_PARAMETER);
        verifyNumericStringLength(rate);
        return new ExchangeRateRequestDto(
                getProcessedCurrencyCode(baseCurrencyCode),
                getProcessedCurrencyCode(targetCurrencyCode),
                rate
        );
    }

    public ExchangeRateRequestDto getExchangeRateRequestDtoForUpdating(HttpServletRequest req) {
        String processedCodePair = getProcessedCurrencyCodePair(req.getPathInfo());
        String processedRate = getProcessedRateForUpdate(req);
        return new ExchangeRateRequestDto(
                getBaseCurrencyCode(processedCodePair),
                getTargetCurrencyCode(processedCodePair),
                processedRate
        );
    }

    public ExchangeRequestDto getExchangeRequestDto(HttpServletRequest req) {
        String baseCurrencyCode = req.getParameter("from");
        String targetCurrencyCode = req.getParameter("to");
        String amountToExchange = req.getParameter("amount");
        ExchangeRateRequestDto exchangeRateRequestDto = getExchangeRateRequestDtoForReceiving(
                baseCurrencyCode + targetCurrencyCode);
        verifyNumericStringLength(amountToExchange);
        return new ExchangeRequestDto(exchangeRateRequestDto, amountToExchange);
    }

    private String getProcessedCurrencyCode(String requestCode) {
        verifyDataPresence(requestCode);
        String processedCode = requestCode.trim()
                .replace("/", "")
                .replace(" ", "")
                .toUpperCase();
        if (processedCode.length() != CURRENCY_CODE_VALID_LENGTH) {
            throw new InvalidRequestDataException(ErrorMessage.INVALID_CURRENCY_CODE);
        }
        return processedCode;
    }

    private String getProcessedCurrencyName(String requestName) {
        verifyDataPresence(requestName);
        String processedName = requestName.trim();
        if (processedName.length() > CURRENCY_NAME_MAX_LENGTH) {
            throw new InvalidRequestDataException(ErrorMessage.INVALID_CURRENCY_NAME);
        }
        return processedName;
    }

    private String getProcessedCurrencySign(String requestSign) {
        verifyDataPresence(requestSign);
        String processedSign = requestSign.trim().replace(" ", "");
        if (processedSign.length() > CURRENCY_SIGN_MAX_LENGTH) {
            throw new InvalidRequestDataException(ErrorMessage.INVALID_CURRENCY_SIGN);
        }
        return processedSign;
    }

    private String getProcessedCurrencyCodePair(String requestCodePair) {
        verifyDataPresence(requestCodePair);
        String processedCodePair = requestCodePair.trim()
                .replace("/", "")
                .replace(" ", "")
                .toUpperCase();
        if (processedCodePair.length() != CURRENCY_CODE_VALID_LENGTH * 2) {
            throw new InvalidRequestDataException(ErrorMessage.INVALID_EXCHANGE_RATE_CODE);
        }
        return processedCodePair;
    }

    private String getProcessedRateForUpdate(HttpServletRequest req) {
        String requestBody = getRequestBodyAsString(req);
        if (requestBody.contains(UPDATE_RATE_PARAMETER)) {
            String rateValue = requestBody.replace(UPDATE_RATE_PARAMETER, "");
            verifyNumericStringLength(rateValue);
            return rateValue;
        }
        throw new CurrencyExchangerException();
    }

    private void verifyNumericStringLength(String numericString) {
        verifyDataPresence(numericString);
        if (numericString.length() > NUMERIC_STRING_MAX_LENGTH) {
            throw new InvalidRequestDataException(ErrorMessage.INVALID_NUMERIC_STRING_LENGTH);
        }
    }

    private String getBaseCurrencyCode(String codePair) {
        return codePair.substring(0, 3);
    }

    private String getTargetCurrencyCode(String codePair) {
        return codePair.substring(3);
    }


    private String getRequestBodyAsString(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new CurrencyExchangerException(e);
        }
        return sb.toString();
    }
}
