package com.xkodxdf.app.controller.servlet.exchange;

import com.xkodxdf.app.controller.RequestDataVerifier;
import com.xkodxdf.app.controller.servlet.BaseServlet;
import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRequestDto;
import com.xkodxdf.app.dto.ExchangeResponseDto;
import com.xkodxdf.app.exception.InvalidInputDataException;
import com.xkodxdf.app.exception.NotAllRequiredParametersPassedException;
import com.xkodxdf.app.service.ExchangeService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchange")
public class ExchangeServlet extends BaseServlet {

    private ExchangeService exchangeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        if (gson == null) {
            super.init(config);
        }
        if (exchangeService == null) {
            String exchangeServiceName = ExchangeService.class.getSimpleName();
            Object attribute = config.getServletContext().getAttribute(exchangeServiceName);
            exchangeService = (ExchangeService) attribute;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeResponseDto exchange = exchangeService.getExchangeEntity(getExchangeRequestDto(req));
        resp.setContentType("application/json");
        setResponse(HttpServletResponse.SC_OK, exchange, resp);
    }

    private ExchangeRequestDto getExchangeRequestDto(HttpServletRequest req) {
        String baseCurrencyCode = req.getParameter("from");
        String targetCurrencyCode = req.getParameter("to");
        String amountString = req.getParameter("amount");
        if (!RequestDataVerifier.isDataPresent(baseCurrencyCode, targetCurrencyCode, amountString)) {
            throw new NotAllRequiredParametersPassedException();
        }
        if (!RequestDataVerifier.canBeConvertedToBigDecimal(amountString)) {
            throw new InvalidInputDataException();
        }
        ExchangeRateRequestDto exchangeRateRequestDto = new ExchangeRateRequestDto(baseCurrencyCode, targetCurrencyCode);
        return new ExchangeRequestDto(exchangeRateRequestDto, new BigDecimal(amountString));
    }
}
