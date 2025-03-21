package com.xkodxdf.app.controller.servlet.currency;

import com.xkodxdf.app.dto.CurrencyRequestDto;
import com.xkodxdf.app.dto.CurrencyResponseDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends BaseCurrencyServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currencyCodeFromUrl = req.getPathInfo();
        CurrencyRequestDto currencyToReceiveDto = verifiedRequestData
                .getCurrencyRequestDtoForReceivingOrDeleting(currencyCodeFromUrl);
        CurrencyResponseDto receivedCurrencyDto = currencyService.get(currencyToReceiveDto);
        setResponse(HttpServletResponse.SC_OK, receivedCurrencyDto, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currencyCodeFromUrl = req.getPathInfo();
        CurrencyRequestDto currencyToDeleteDto = verifiedRequestData
                .getCurrencyRequestDtoForReceivingOrDeleting(currencyCodeFromUrl);
        CurrencyResponseDto deletedCurrencyDto = currencyService.delete(currencyToDeleteDto);
        setResponse(HttpServletResponse.SC_OK, deletedCurrencyDto, resp);
    }
}
