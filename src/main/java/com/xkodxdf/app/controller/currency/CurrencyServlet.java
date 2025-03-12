package com.xkodxdf.app.controller.currency;

import com.google.gson.Gson;
import com.xkodxdf.app.model.entity.CurrencyEntity;
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
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        int excludeSlashSubstringIndex = 1;
        String code = req.getPathInfo().substring(excludeSlashSubstringIndex);
        CurrencyEntity currency = currencyService.get(code);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(currency));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        int excludeSlashSubstringIndex = 1;
        String code = req.getPathInfo().substring(excludeSlashSubstringIndex);
        CurrencyEntity deletedCurrency = currencyService.delete(code);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(deletedCurrency));
    }
}
