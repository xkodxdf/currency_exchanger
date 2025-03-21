package com.xkodxdf.app.controller.servlet.currency;

import com.xkodxdf.app.controller.servlet.BaseServlet;
import com.xkodxdf.app.service.CurrencyService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;

public abstract class BaseCurrencyServlet extends BaseServlet {

    protected CurrencyService currencyService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        if (!baseServletInitialized) {
            super.init(config);
        }
        if (currencyService == null) {
            String currencyServiceName = CurrencyService.class.getSimpleName();
            Object attribute = config.getServletContext().getAttribute(currencyServiceName);
            currencyService = (CurrencyService) attribute;
        }
    }
}
