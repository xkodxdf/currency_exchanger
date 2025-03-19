package com.xkodxdf.app.controller.servlet.currency;

import com.xkodxdf.app.dto.CurrencyRequestDto;
import com.xkodxdf.app.dto.CurrencyResponseDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends BaseCurrencyServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = getCode(req);
        CurrencyResponseDto currency = currencyService.get(new CurrencyRequestDto(code));
        setResponse(HttpServletResponse.SC_OK, currency, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = getCode(req);
        CurrencyResponseDto deletedCurrency = currencyService.delete(new CurrencyRequestDto(code));
        setResponse(HttpServletResponse.SC_OK, deletedCurrency, resp);
    }
}
