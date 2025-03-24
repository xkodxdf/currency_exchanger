package com.xkodxdf.app.service;

import com.xkodxdf.app.ErrorMessage;
import com.xkodxdf.app.dto.CurrencyRequestDto;
import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRequestDto;
import com.xkodxdf.app.exception.InvalidRequestDataException;

import java.math.BigDecimal;

public class RequestDtoValidator {

    public void validateCurrencyRequestDtoForReceivengOrDeleting(CurrencyRequestDto requestDto) {
        validateCurrencyCode(requestDto.code());
    }

    public void validateCurrencyRequestDtoForSaving(CurrencyRequestDto requestDto) {
        validateCurrencyName(requestDto.name());
        validateCurrencyCode(requestDto.code());
    }

    public void validateExchangeRateRequestDtoForReceiving(ExchangeRateRequestDto requestDto) {
        validateCurrencyCode(requestDto.baseCurrencyCode());
        validateCurrencyCode(requestDto.targetCurrencyCode());
    }

    public void validateExchangeRateRequestDtoForSavingOrUpdating(ExchangeRateRequestDto requestDto) {
        validateExchangeRateRequestDtoForReceiving(requestDto);
        validateNumericString(requestDto.rate());
    }

    public void validateExchangeRequestDto(ExchangeRequestDto exchangeRequestDto) {
        validateExchangeRateRequestDtoForReceiving(exchangeRequestDto.exchangeRateRequestDto());
        validateNumericString(exchangeRequestDto.amount());
        validateAmountToConvert(new BigDecimal(exchangeRequestDto.amount()));
    }

    private void validateCurrencyName(String name) {
        String nameWithExtraSpacesRemoved = name.replaceAll("\\s+", " ");
        boolean isNameValid = nameWithExtraSpacesRemoved.matches("^[a-zA-Z ]+$");
        if (!isNameValid) {
            throw new InvalidRequestDataException(ErrorMessage.INVALID_CURRENCY_NAME);
        }
    }

    private void validateCurrencyCode(String code) {
        boolean isCodeValid = code.matches("^[A-Z]+$");
        if (!isCodeValid) {
            throw new InvalidRequestDataException(ErrorMessage.INVALID_CURRENCY_CODE);
        }
    }

    private void validateNumericString(String numericString) {
        try {
            BigDecimal decimal = new BigDecimal(numericString);
        } catch (NumberFormatException e) {
            throw new InvalidRequestDataException(ErrorMessage.BIG_DECIMAL_CONVERSION_ERR);
        }
    }

    private void validateAmountToConvert(BigDecimal amount) {
        BigDecimal minAmountValue = new BigDecimal("0.01");
        if (amount.compareTo(minAmountValue) < 0) {
            throw new InvalidRequestDataException(ErrorMessage.AMOUNT_TO_CONVERT_TOO_SMALL_ERR);
        }
    }
}
