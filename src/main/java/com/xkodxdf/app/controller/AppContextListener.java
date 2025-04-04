package com.xkodxdf.app.controller;

import com.google.gson.Gson;
import com.xkodxdf.app.exception.CurrencyExchangerException;
import com.xkodxdf.app.model.dao.CurrencyDaoImpl;
import com.xkodxdf.app.model.dao.ExchangeRateDaoImpl;
import com.xkodxdf.app.model.dao.HikariCPDataSource;
import com.xkodxdf.app.model.dao.SqlHelper;
import com.xkodxdf.app.service.CurrencyService;
import com.xkodxdf.app.service.ExchangeRateService;
import com.xkodxdf.app.service.ExchangeService;
import com.xkodxdf.app.service.RequestDtoValidator;
import com.zaxxer.hikari.HikariDataSource;
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
        closeDbConnections(sce);
        deregisterDbDriver();
    }

    private void setContextAttributes(ServletContextEvent sce) {
        var servletContext = sce.getServletContext();
        servletContext.setAttribute(VerifiedRequestDataProvider.class.getSimpleName(), new VerifiedRequestDataProvider());
        servletContext.setAttribute(Gson.class.getSimpleName(), new Gson());
        var dataSource = HikariCPDataSource.getDataSource();
        servletContext.setAttribute(HikariDataSource.class.getSimpleName(), dataSource);
        var sqlHelper = new SqlHelper(dataSource);
        var requestDtoValidator = new RequestDtoValidator();
        var currencyService = new CurrencyService(new CurrencyDaoImpl(sqlHelper), requestDtoValidator);
        servletContext.setAttribute(CurrencyService.class.getSimpleName(), currencyService);
        var exchangeRateDao = new ExchangeRateDaoImpl(sqlHelper);
        var exchangeRateService = new ExchangeRateService(exchangeRateDao, requestDtoValidator);
        servletContext.setAttribute(ExchangeRateService.class.getSimpleName(), exchangeRateService);
        var exchangeService = new ExchangeService(exchangeRateDao, requestDtoValidator);
        servletContext.setAttribute(ExchangeService.class.getSimpleName(), exchangeService);
    }

    private void closeDbConnections(ServletContextEvent sce) {
        Object attribute = sce.getServletContext().getAttribute(HikariDataSource.class.getSimpleName());
        try {
            HikariDataSource dataSource = (HikariDataSource) attribute;
            dataSource.close();
        } catch (Exception e) {
            throw new CurrencyExchangerException();
        }
    }

    private void deregisterDbDriver() {
        try {
            String driverUrl = "jdbc:postgresql://";
            DriverManager.deregisterDriver(DriverManager.getDriver(driverUrl));
        } catch (Exception e) {
            throw new CurrencyExchangerException(e);
        }
    }
}
