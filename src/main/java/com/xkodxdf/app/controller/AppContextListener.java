package com.xkodxdf.app.controller;

import com.google.gson.Gson;
import com.xkodxdf.app.model.dao.CurrencyDaoImpl;
import com.xkodxdf.app.model.dao.ExchangeRateDaoImpl;
import com.xkodxdf.app.service.CurrencyService;
import com.xkodxdf.app.service.ExchangeRateService;
import com.xkodxdf.app.service.ExchangeService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute(Gson.class.getSimpleName(), new Gson());
        CurrencyService currencyService = new CurrencyService(CurrencyDaoImpl.getInstance());
        servletContext.setAttribute(CurrencyService.class.getSimpleName(), currencyService);
        ExchangeRateService exchangeRateService = new ExchangeRateService(ExchangeRateDaoImpl.getInstance());
        servletContext.setAttribute(ExchangeRateService.class.getSimpleName(), exchangeRateService);
        ExchangeService exchangeService = new ExchangeService(ExchangeRateDaoImpl.getInstance());
        servletContext.setAttribute(ExchangeService.class.getSimpleName(), exchangeService);
    }
}
