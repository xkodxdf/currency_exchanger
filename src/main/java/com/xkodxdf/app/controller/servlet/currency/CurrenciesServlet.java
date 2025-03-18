package com.xkodxdf.app.controller.servlet.currency;

import com.google.gson.Gson;
import com.xkodxdf.app.exception.NotAllRequiredParametersPassedException;
import com.xkodxdf.app.dto.CurrencyRequestDto;
import com.xkodxdf.app.dto.CurrencyResponseDto;
import com.xkodxdf.app.service.CurrencyService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    private Gson gson;
    private CurrencyService currencyService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.gson = (Gson) servletContext.getAttribute(Gson.class.getSimpleName());
        this.currencyService = (CurrencyService) servletContext.getAttribute(CurrencyService.class.getSimpleName());
    }

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
        if (name == null || code == null || sign == null
            || name.isEmpty() || code.isEmpty() || sign.isEmpty()) {
            throw new NotAllRequiredParametersPassedException();
        }
        CurrencyResponseDto savedCurrency = currencyService.save(new CurrencyRequestDto(name, code, sign));
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(gson.toJson(savedCurrency));
    }
}
