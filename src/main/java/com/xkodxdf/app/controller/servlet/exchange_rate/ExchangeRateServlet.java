package com.xkodxdf.app.controller.servlet.exchange_rate;

import com.google.gson.Gson;
import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRateResponseDto;
import com.xkodxdf.app.exception.CurrencyExchangerException;
import com.xkodxdf.app.exception.InvalidExchangeRateCodeException;
import com.xkodxdf.app.exception.InvalidInputDataException;
import com.xkodxdf.app.exception.NotAllRequiredParametersPassedException;
import com.xkodxdf.app.service.ExchangeRateService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private Gson gson;
    private ExchangeRateService exchangeRateService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        gson = (Gson) servletContext.getAttribute(Gson.class.getSimpleName());
        exchangeRateService = (ExchangeRateService) servletContext.getAttribute(ExchangeRateService.class.getSimpleName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRateResponseDto exchangeRate = exchangeRateService.get(getExchangeRateRequestDto(req));
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(exchangeRate));
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
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(updatedExchangeRate));
    }

    private ExchangeRateRequestDto getExchangeRateRequestDto(HttpServletRequest req) {
        String codes = getCodes(req);
        try {
            BigDecimal newRate = new BigDecimal(getNewRateString(req));
            return new ExchangeRateRequestDto(getBaseCurrencyCode(codes), getTargetCurrencyCode(codes), newRate);
        } catch (NumberFormatException e) {
            throw new InvalidInputDataException();
        }

    }

    private String getCodes(HttpServletRequest req) {
        String codesInUrl = req.getPathInfo();
        int codeWithSlashLength = 7;
        if (codesInUrl == null || codesInUrl.length() != codeWithSlashLength) {
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
        if (requestBody.isEmpty() || rateParameter.equals(requestBody)) {
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
