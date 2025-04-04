package com.xkodxdf.app.controller.servlet.exchange_rate;

import com.xkodxdf.app.controller.servlet.BaseServlet;
import com.xkodxdf.app.service.ExchangeRateService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;

public abstract class BaseExchangeRateServlet extends BaseServlet {

    protected ExchangeRateService exchangeRateService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        if (!baseServletInitialized) {
            super.init(config);
        }
        if (exchangeRateService == null) {
            exchangeRateService = getAttributeFromContext(ExchangeRateService.class, config);
        }
    }
}
