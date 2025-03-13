package com.xkodxdf.app.controller.exchange;

import com.google.gson.Gson;
import com.xkodxdf.app.model.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.model.dto.ExchangeRequestDto;
import com.xkodxdf.app.model.dto.ExchangeResponseDto;
import com.xkodxdf.app.model.service.ExchangeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final ExchangeService exchangeService = ExchangeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String baseCurrencyCode = req.getParameter("from");
        String targetCurrencyCode = req.getParameter("to");
        String amountString = req.getParameter("amount");
        BigDecimal amount = new BigDecimal(amountString);
        ExchangeResponseDto exchangeEntity = exchangeService.getExchangeEntity(
                new ExchangeRequestDto(new ExchangeRateRequestDto(baseCurrencyCode, targetCurrencyCode), amount)
        );
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(exchangeEntity));
    }
}
