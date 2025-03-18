package com.xkodxdf.app.controller.servlet.exchange_rate;

import com.google.gson.Gson;
import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRateResponseDto;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

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
        List<ExchangeRateResponseDto> exchangeRates = exchangeRateService.getAll();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(exchangeRates));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRateResponseDto saveExchangeRate = exchangeRateService.save(getRequestDto(req));
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(gson.toJson(saveExchangeRate));
    }

    private ExchangeRateRequestDto getRequestDto(HttpServletRequest req) {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        String rateString = req.getParameter("rate");
        if (baseCurrencyCode == null || targetCurrencyCode == null || rateString == null
            || baseCurrencyCode.isEmpty() || targetCurrencyCode.isEmpty() || rateString.isEmpty()) {
            throw new NotAllRequiredParametersPassedException();
        }
        try {
            BigDecimal rate = new BigDecimal(rateString);
            return new ExchangeRateRequestDto(baseCurrencyCode, targetCurrencyCode, rate);
        } catch (NumberFormatException e) {
            throw new InvalidInputDataException();
        }
    }
}
