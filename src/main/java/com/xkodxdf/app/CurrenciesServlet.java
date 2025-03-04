package com.xkodxdf.app;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CurrencyDAO currencyDAO = new CurrencyDAO(new ConnectionProvider());
        List<CurrencyDTO> currencyDTOS = currencyDAO.getAllSorted();
        resp.getWriter().write(new Gson().toJson(currencyDTOS));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CurrencyDAO currencyDAO = new CurrencyDAO(new ConnectionProvider());
        String sign = req.getParameter("sign");
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        currencyDAO.add(new Currency(sign, code, name));
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(new Gson().toJson(currencyDAO.get(code)));
    }
}
