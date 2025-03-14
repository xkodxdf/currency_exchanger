package com.xkodxdf.app.controller.currency;

import com.google.gson.Gson;
import com.xkodxdf.app.model.dto.CurrencyRequestDto;
import com.xkodxdf.app.model.dto.CurrencyResponseDto;
import com.xkodxdf.app.model.service.CurrencyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int excludeSlashSubstringIndex = 1;
        String code = req.getPathInfo().substring(excludeSlashSubstringIndex);
        CurrencyResponseDto currency = currencyService.get(new CurrencyRequestDto(code));
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(currency));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int excludeSlashSubstringIndex = 1;
        String code = req.getPathInfo().substring(excludeSlashSubstringIndex);
        CurrencyResponseDto deletedCurrency = currencyService.delete(new CurrencyRequestDto(code));
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(deletedCurrency));
    }
}
