package com.xkodxdf.app.controller.servlet.exchange_rate;

import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRateResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends BaseExchangeRateServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currencyCodePairFromUrl = req.getPathInfo();
        ExchangeRateRequestDto exchangeRateToReceiveDto = verifiedRequestData
                .getExchangeRateRequestDtoForReceiving(currencyCodePairFromUrl);
        ExchangeRateResponseDto receivedExchangeRateDto = exchangeRateService.get(exchangeRateToReceiveDto);
        setResponse(HttpServletResponse.SC_OK, receivedExchangeRateDto, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String patchMethodName = "PATCH";
        if (patchMethodName.equalsIgnoreCase(req.getMethod())) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    private void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExchangeRateRequestDto exchangeRateForUpdateDto = verifiedRequestData.getExchangeRateRequestDtoForUpdating(req);
        ExchangeRateResponseDto updatedExchangeRateDto = exchangeRateService.update(exchangeRateForUpdateDto);
        setResponse(HttpServletResponse.SC_OK, updatedExchangeRateDto, resp);
    }
}
