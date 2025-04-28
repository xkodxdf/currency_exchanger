package com.xkodxdf.app.servlet;

import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRateResponseDto;
import com.xkodxdf.app.service.ExchangeRateService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends BaseServlet {

    private ExchangeRateService exchangeRateService;

    @Override
    public void init(ServletConfig config) {
        super.init(config);
        exchangeRateService = getAttributeFromContext(ExchangeRateService.class, config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ExchangeRateResponseDto> allExchangeRates = exchangeRateService.getAll();
        setResponse(HttpServletResponse.SC_OK, allExchangeRates, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExchangeRateRequestDto exchangeRateToSaveDto = verifiedRequestData.getExchangeRateRequestDtoForSaving(req);
        ExchangeRateResponseDto savedExchangeRateDto = exchangeRateService.save(exchangeRateToSaveDto);
        setResponse(HttpServletResponse.SC_CREATED, savedExchangeRateDto, resp);
    }
}
