package com.xkodxdf.app.controller.servlet.currency;

import com.xkodxdf.app.dto.CurrencyResponseDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends BaseCurrencyServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<CurrencyResponseDto> requestedCurrencies = currencyService.getAll();
        setResponse(HttpServletResponse.SC_OK, requestedCurrencies, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CurrencyResponseDto savedCurrency = currencyService.save(getCurrencyRequestDto(req));
        setResponse(HttpServletResponse.SC_CREATED, savedCurrency, resp);
    }
}
