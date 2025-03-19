package com.xkodxdf.app.controller.servlet.currency;

import com.xkodxdf.app.controller.RequestDataVerifier;
import com.xkodxdf.app.controller.servlet.BaseServlet;
import com.xkodxdf.app.dto.CurrencyRequestDto;
import com.xkodxdf.app.exception.InvalidCurrencyCodeException;
import com.xkodxdf.app.exception.NotAllRequiredParametersPassedException;
import com.xkodxdf.app.service.CurrencyService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public abstract class BaseCurrencyServlet extends BaseServlet {

    protected CurrencyService currencyService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        if (gson == null) {
            super.init(config);
        }
        if (currencyService == null) {
            String currencyServiceName = CurrencyService.class.getSimpleName();
            Object attribute = config.getServletContext().getAttribute(currencyServiceName);
            currencyService = (CurrencyService) attribute;
        }
    }

    protected CurrencyRequestDto getCurrencyRequestDto(HttpServletRequest req) {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");
        if (!RequestDataVerifier.isDataPresent(name, code, sign)) {
            throw new NotAllRequiredParametersPassedException();
        }
        return new CurrencyRequestDto(name, code, sign);
    }

    protected String getCode(HttpServletRequest req) {
        String codeInUrl = req.getPathInfo();
        if (!RequestDataVerifier.isUrlCurrencyCodeLegal(codeInUrl)) {
            throw new InvalidCurrencyCodeException();
        }
        int withoutSlashIndex = 1;
        return codeInUrl.substring(withoutSlashIndex);
    }
}
