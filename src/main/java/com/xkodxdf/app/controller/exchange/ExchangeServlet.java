package com.xkodxdf.app.controller.exchange;

import com.google.gson.Gson;
import com.xkodxdf.app.model.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.model.dto.ExchangeRequestDto;
import com.xkodxdf.app.model.dto.ExchangeResponseDto;
import com.xkodxdf.app.model.service.ExchangeService;
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
        String baseCurrencyCode = req.getParameter("from");
        String targetCurrencyCode = req.getParameter("to");
        String amountString = req.getParameter("amount");
        BigDecimal amount = new BigDecimal(amountString);
        ExchangeRateRequestDto exchangeRateRequestDto = new ExchangeRateRequestDto(baseCurrencyCode, targetCurrencyCode);
        ExchangeRequestDto exchangeRequestDto = new ExchangeRequestDto(exchangeRateRequestDto, amount);
        ExchangeResponseDto exchange = exchangeService.getExchangeEntity(exchangeRequestDto);
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(exchange));
    }
}
