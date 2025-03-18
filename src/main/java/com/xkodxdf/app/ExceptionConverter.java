package com.xkodxdf.app;

import com.xkodxdf.app.controller.ErrorResponse;
import com.xkodxdf.app.exception.*;
import jakarta.servlet.http.HttpServletResponse;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public final class ExceptionConverter {

    private ExceptionConverter() {
    }


    public static ErrorResponse convertExceptionToErrorResponse(Throwable t) {
        if (t instanceof InvalidInputDataException) {
            return new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, ErrorMessage.INPUT_DATA_ERR);
        }
        if (t instanceof DataAlreadyExistException) {
            return new ErrorResponse(HttpServletResponse.SC_CONFLICT, ErrorMessage.DUPLICATION_ERR);
        }
        if (t instanceof DataNotFoundExcepton) {
            return new ErrorResponse(HttpServletResponse.SC_NOT_FOUND, ErrorMessage.NOT_FOUND_ERR);
        }
        if (t instanceof InvalidCurrencyCodeException) {
            return new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, ErrorMessage.INVALID_CURRENCY_CODE);
        }
        if (t instanceof InvalidExchangeRateCodeException) {
            return new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, ErrorMessage.INVALID_EXCHANGE_RATE_CODE_ERR);
        }
        if (t instanceof NotAllRequiredParametersPassedException) {
            return new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, ErrorMessage.REQUIRED_PARAMS_ERR);
        }
        return new ErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ErrorMessage.UNEXPECTED_ERR);
    }

    public static CurrencyExchangerException convertSqlToCurrencyExchangerException(SQLException e) {
        if (e instanceof PSQLException) {
            String sqlState = e.getSQLState();
            String duplicateErrCode = "23505";
            String nullErr = "23502";
            if (duplicateErrCode.equals(sqlState)) {
                return new DataAlreadyExistException(e);
            }
            if (nullErr.equals(sqlState)) {
                return new DataNotFoundExcepton();
            }
        }
        return new CurrencyExchangerException(e);
    }
}
