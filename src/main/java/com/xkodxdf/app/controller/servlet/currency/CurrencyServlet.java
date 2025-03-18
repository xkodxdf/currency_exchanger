package com.xkodxdf.app.controller.servlet.currency;

import com.google.gson.Gson;
import com.xkodxdf.app.exception.InvalidCurrencyCodeException;
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

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {

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
        String code = getCode(req);
        CurrencyResponseDto currency = currencyService.get(new CurrencyRequestDto(code));
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(currency));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = getCode(req);
        CurrencyResponseDto deletedCurrency = currencyService.delete(new CurrencyRequestDto(code));
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(deletedCurrency));
    }

    private String getCode(HttpServletRequest req) {
        String codeInUrl = req.getPathInfo();
        int codeWithSlashLength = 4;
        if (codeInUrl == null || codeInUrl.length() != codeWithSlashLength) {
            throw new InvalidCurrencyCodeException();
        }
        int withoutSlashIndex = 1;
        return codeInUrl.substring(withoutSlashIndex);
    }
}
