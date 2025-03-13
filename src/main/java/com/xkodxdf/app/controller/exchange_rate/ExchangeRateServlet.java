package com.xkodxdf.app.controller.exchange_rate;

import com.google.gson.Gson;
import com.xkodxdf.app.exception.CurrencyExchangerException;
import com.xkodxdf.app.model.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.model.dto.ExchangeRateResponseDto;
import com.xkodxdf.app.model.service.ExchangeRateService;
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

    private final Gson gson = new Gson();
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        int excludeSlashSubstringIndex = 1;
        String codes = req.getPathInfo().substring(excludeSlashSubstringIndex);
        ExchangeRateResponseDto exchangeRate = exchangeRateService.get(
                new ExchangeRateRequestDto(getBaseCurrencyCode(codes), getTargetCurrencyCode(codes)));
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
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        int excludeSlashSubstringIndex = 1;
        String codes = req.getPathInfo().substring(excludeSlashSubstringIndex);
        BigDecimal newRate = new BigDecimal(getNewRateString(req));
        ExchangeRateResponseDto updatedExchangeRate =  exchangeRateService.update(
                new ExchangeRateRequestDto(getBaseCurrencyCode(codes), getTargetCurrencyCode(codes), newRate));
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(updatedExchangeRate));
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
        String newRateString;
        if (requestBody.contains("rate=")) {
            newRateString = requestBody.substring(5);
        } else {
            throw new CurrencyExchangerException();
        }
        return newRateString;
    }

    private String getBaseCurrencyCode(String codePair) {
        return codePair.substring(0, 3);
    }

    private String getTargetCurrencyCode(String codePair) {
        return codePair.substring(3);
    }
}
