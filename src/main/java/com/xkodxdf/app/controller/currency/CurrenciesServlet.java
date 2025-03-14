package com.xkodxdf.app.controller.currency;

import com.google.gson.Gson;
import com.xkodxdf.app.model.dto.CurrencyRequestDto;
import com.xkodxdf.app.model.dto.CurrencyResponseDto;
import com.xkodxdf.app.model.service.CurrencyService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<CurrencyResponseDto> currencies = currencyService.getAll();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(currencies));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");
        CurrencyResponseDto savedCurrency = currencyService.save(new CurrencyRequestDto(name, code, sign));
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(gson.toJson(savedCurrency));
    }
}
