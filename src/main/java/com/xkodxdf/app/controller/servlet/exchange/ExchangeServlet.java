package com.xkodxdf.app.controller.servlet.exchange;

import com.google.gson.Gson;
import com.xkodxdf.app.exception.InvalidInputDataException;
import com.xkodxdf.app.exception.NotAllRequiredParametersPassedException;
import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRequestDto;
import com.xkodxdf.app.dto.ExchangeResponseDto;
import com.xkodxdf.app.service.ExchangeService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {

    private Gson gson;
    private ExchangeService exchangeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        gson = (Gson) servletContext.getAttribute(Gson.class.getSimpleName());
        exchangeService = (ExchangeService) servletContext.getAttribute(ExchangeService.class.getSimpleName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeResponseDto exchange = exchangeService.getExchangeEntity(getExchangeRequestDto(req));
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(exchange));
    }

    private ExchangeRequestDto getExchangeRequestDto(HttpServletRequest req) {
        String baseCurrencyCode = req.getParameter("from");
        String targetCurrencyCode = req.getParameter("to");
        String amountString = req.getParameter("amount");
        if (baseCurrencyCode == null || targetCurrencyCode == null || amountString == null
            || baseCurrencyCode.isEmpty() || targetCurrencyCode.isEmpty() || amountString.isEmpty()) {
            throw new NotAllRequiredParametersPassedException();
        }
        try {
            BigDecimal amount = new BigDecimal(amountString);
            ExchangeRateRequestDto exchangeRateRequestDto = new ExchangeRateRequestDto(baseCurrencyCode, targetCurrencyCode);
            return new ExchangeRequestDto(exchangeRateRequestDto, amount);
        } catch (NumberFormatException e) {
            throw new InvalidInputDataException();
        }
    }
}
