package com.xkodxdf.app.controller.servlet.exchange_rate;

import com.xkodxdf.app.controller.RequestDataVerifier;
import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRateResponseDto;
import com.xkodxdf.app.exception.InvalidInputDataException;
import com.xkodxdf.app.exception.NotAllRequiredParametersPassedException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends BaseExchangeRateServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ExchangeRateResponseDto> requestedExchangeRates = exchangeRateService.getAll();
        setResponse(HttpServletResponse.SC_OK, requestedExchangeRates, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExchangeRateResponseDto savedExchangeRate = exchangeRateService.save(getRequestDto(req));
        setResponse(HttpServletResponse.SC_CREATED, savedExchangeRate, resp);
    }

    private ExchangeRateRequestDto getRequestDto(HttpServletRequest req) {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        String rateString = req.getParameter("rate");
        if (!RequestDataVerifier.isDataPresent(baseCurrencyCode, targetCurrencyCode, rateString)) {
            throw new NotAllRequiredParametersPassedException();
        }
        if (!RequestDataVerifier.canBeConvertedToBigDecimal(rateString)) {
            throw new InvalidInputDataException();
        }
        return new ExchangeRateRequestDto(baseCurrencyCode, targetCurrencyCode, new BigDecimal(rateString));
    }
}
