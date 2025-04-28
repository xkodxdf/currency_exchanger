package com.xkodxdf.app.servlet;

import com.xkodxdf.app.dto.ExchangeRequestDto;
import com.xkodxdf.app.dto.ExchangeResponseDto;
import com.xkodxdf.app.service.ExchangeService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/exchange")
public class ExchangeServlet extends BaseServlet {

    private ExchangeService exchangeService;

    @Override
    public void init(ServletConfig config) {
        super.init(config);
        exchangeService = getAttributeFromContext(ExchangeService.class, config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExchangeRequestDto exchangeRequestDto = verifiedRequestData.getExchangeRequestDto(req);
        ExchangeResponseDto exchangeResultDto = exchangeService.getExchangeResponseDto(exchangeRequestDto);
        resp.setContentType("application/json");
        setResponse(HttpServletResponse.SC_OK, exchangeResultDto, resp);
    }
}
