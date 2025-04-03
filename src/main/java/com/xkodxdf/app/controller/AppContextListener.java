package com.xkodxdf.app.controller;

import com.google.gson.Gson;
import com.xkodxdf.app.ErrorMessage;
import com.xkodxdf.app.exception.CurrencyExchangerException;
import com.xkodxdf.app.model.dao.ConnectionProvider;
import com.xkodxdf.app.model.dao.CurrencyDaoImpl;
import com.xkodxdf.app.model.dao.ExchangeRateDaoImpl;
import com.xkodxdf.app.service.CurrencyService;
import com.xkodxdf.app.service.ExchangeRateService;
import com.xkodxdf.app.service.ExchangeService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.DriverManager;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        setContextAttributes(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        closeDbConnectionPool(sce);
        deregisterDbDriver();
    }

    private void setContextAttributes(ServletContextEvent sce) {
        var servletContext = sce.getServletContext();
        servletContext.setAttribute(VerifiedRequestDataProvider.class.getSimpleName(), new VerifiedRequestDataProvider());
        servletContext.setAttribute(Gson.class.getSimpleName(), new Gson());
        var connectionProvider = new ConnectionProvider();
        servletContext.setAttribute(ConnectionProvider.class.getSimpleName(), connectionProvider);
        var currencyService = new CurrencyService(new CurrencyDaoImpl(connectionProvider));
        servletContext.setAttribute(CurrencyService.class.getSimpleName(), currencyService);
        var exchangeRateDao = new ExchangeRateDaoImpl(connectionProvider);
        var exchangeRateService = new ExchangeRateService(exchangeRateDao);
        servletContext.setAttribute(ExchangeRateService.class.getSimpleName(), exchangeRateService);
        var exchangeService = new ExchangeService(exchangeRateDao);
        servletContext.setAttribute(ExchangeService.class.getSimpleName(), exchangeService);
    }

    private void closeDbConnectionPool(ServletContextEvent sce) {
        try {
            ServletContext servletContext = sce.getServletContext();
            Object attribute = servletContext.getAttribute(ConnectionProvider.class.getSimpleName());
            ConnectionProvider connectionProvider = (ConnectionProvider) attribute;
            connectionProvider.closeConnections();
        } catch (Exception e) {
            throw new CurrencyExchangerException(ErrorMessage.UNEXPECTED_ERR, e);
        }
    }

    private void deregisterDbDriver() {
        try {
            String driverUrl = "jdbc:postgresql://";
            DriverManager.deregisterDriver(DriverManager.getDriver(driverUrl));
        } catch (Exception e) {
            throw new CurrencyExchangerException(ErrorMessage.UNEXPECTED_ERR, e);
        }
    }
}
