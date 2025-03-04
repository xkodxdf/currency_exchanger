package com.xkodxdf.app;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int excludeSlashSubstringIndex = 1;
        String code = req.getPathInfo().substring(excludeSlashSubstringIndex);
        CurrencyDAO currencyDAO = new CurrencyDAO(new ConnectionProvider());
        CurrencyDTO currencyDTO = currencyDAO.get(code);
        resp.getWriter().write(new Gson().toJson(currencyDTO));
    }
}
