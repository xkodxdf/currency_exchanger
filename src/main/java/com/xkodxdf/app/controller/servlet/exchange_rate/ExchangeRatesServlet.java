package com.xkodxdf.app.controller.servlet.exchange_rate;

import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRateResponseDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends BaseExchangeRateServlet {

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
