package com.xkodxdf.app.controller.servlet.exchange_rate;

import com.xkodxdf.app.controller.RequestDataVerifier;
import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRateResponseDto;
import com.xkodxdf.app.exception.CurrencyExchangerException;
import com.xkodxdf.app.exception.InvalidExchangeRateCodeException;
import com.xkodxdf.app.exception.InvalidInputDataException;
import com.xkodxdf.app.exception.NotAllRequiredParametersPassedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends BaseExchangeRateServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRateResponseDto exchangeRate = exchangeRateService.get(getExchangeRateRequestDto(req));
        setResponse(HttpServletResponse.SC_OK, exchangeRate, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String patchMethodName = "PATCH";
        if (patchMethodName.equalsIgnoreCase(req.getMethod())) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    private void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExchangeRateResponseDto updatedExchangeRate = exchangeRateService.update(getExchangeRateRequestDto(req));
        setResponse(HttpServletResponse.SC_OK, updatedExchangeRate, resp);
    }

    private ExchangeRateRequestDto getExchangeRateRequestDto(HttpServletRequest req) {
        String codes = getCodes(req);
        String newRateString = getNewRateString(req);
        if (!RequestDataVerifier.canBeConvertedToBigDecimal(newRateString)) {
            throw new InvalidInputDataException();
        }
        BigDecimal newRate = new BigDecimal(newRateString);
        return new ExchangeRateRequestDto(getBaseCurrencyCode(codes), getTargetCurrencyCode(codes), newRate);
    }

    private String getCodes(HttpServletRequest req) {
        String codesInUrl = req.getPathInfo();
        if (!RequestDataVerifier.isUrlCurrencyCodePairLegal(codesInUrl)) {
            throw new InvalidExchangeRateCodeException();
        }
        int withoutSlashIndex = 1;
        return codesInUrl.substring(withoutSlashIndex);
    }

    private static String getNewRateString(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new CurrencyExchangerException(e);
        }
        String requestBody = sb.toString();
        String rateParameter = "rate=";
        if (!RequestDataVerifier.isDataPresent(rateParameter)) {
            throw new NotAllRequiredParametersPassedException();
        }
        if (requestBody.contains(rateParameter)) {
            return requestBody.substring(rateParameter.length());
        }
        throw new CurrencyExchangerException();
    }

    private String getBaseCurrencyCode(String codePair) {
        return codePair.substring(0, 3);
    }

    private String getTargetCurrencyCode(String codePair) {
        return codePair.substring(3);
    }
}
