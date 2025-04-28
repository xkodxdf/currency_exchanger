package com.xkodxdf.app.servlet;

import com.xkodxdf.app.dto.CurrencyResponseDto;
import com.xkodxdf.app.service.CurrencyService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends BaseServlet {

    private CurrencyService currencyService;

    @Override
    public void init(ServletConfig config) {
        super.init(config);
        currencyService = getAttributeFromContext(CurrencyService.class, config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currencyCodeFromUrl = req.getPathInfo();
        var currencyToReceiveDto = verifiedRequestData.getCurrencyRequestDtoForReceivingOrDeleting(currencyCodeFromUrl);
        CurrencyResponseDto receivedCurrencyDto = currencyService.get(currencyToReceiveDto);
        setResponse(HttpServletResponse.SC_OK, receivedCurrencyDto, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currencyCodeFromUrl = req.getPathInfo();
        var currencyToDeleteDto = verifiedRequestData.getCurrencyRequestDtoForReceivingOrDeleting(currencyCodeFromUrl);
        CurrencyResponseDto deletedCurrencyDto = currencyService.delete(currencyToDeleteDto);
        setResponse(HttpServletResponse.SC_OK, deletedCurrencyDto, resp);
    }
}
