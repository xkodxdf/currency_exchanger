package com.xkodxdf.app.servlet;

import com.xkodxdf.app.dto.CurrencyRequestDto;
import com.xkodxdf.app.dto.CurrencyResponseDto;
import com.xkodxdf.app.service.CurrencyService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends BaseServlet {

    private CurrencyService currencyService;

    @Override
    public void init(ServletConfig config) {
        super.init(config);
        currencyService = getAttributeFromContext(CurrencyService.class, config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<CurrencyResponseDto> requestedCurrencies = currencyService.getAll();
        setResponse(HttpServletResponse.SC_OK, requestedCurrencies, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CurrencyRequestDto currencyDtoToSave = verifiedRequestData.getCurrencyRequestDtoForSaving(req);
        CurrencyResponseDto savedCurrencyDto = currencyService.save(currencyDtoToSave);
        setResponse(HttpServletResponse.SC_CREATED, savedCurrencyDto, resp);
    }
}
