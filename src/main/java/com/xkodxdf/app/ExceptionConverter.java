package com.xkodxdf.app;

import com.xkodxdf.app.controller.ErrorResponse;
import com.xkodxdf.app.exception.*;
import jakarta.servlet.http.HttpServletResponse;
import org.postgresql.util.PSQLException;

public final class ExceptionConverter {

    private ExceptionConverter() {
    }

    public static ErrorResponse toErrorResponse(Throwable t) {
        if (t instanceof InvalidRequestDataException) {
            return new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, t.getMessage());
        }
        if (t instanceof DataAlreadyExistException) {
            return new ErrorResponse(HttpServletResponse.SC_CONFLICT, t.getMessage());
        }
        if (t instanceof DataNotFoundException) {
            return new ErrorResponse(HttpServletResponse.SC_NOT_FOUND, t.getMessage());
        }
        return new ErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, t.getMessage());
    }

    public static CurrencyExchangerException toCurrencyExchangerException(Exception e) {
        if (e instanceof PSQLException psqlException) {
            String sqlState = psqlException.getSQLState();
            String duplicateErrCode = "23505";
            String nullErrCode = "23502";
            String emptyResultSet = "24000";

            if (duplicateErrCode.equals(sqlState)) {
                return new DataAlreadyExistException(e);
            }
            if (nullErrCode.equals(sqlState) || emptyResultSet.equals(sqlState)) {
                return new DataNotFoundException(e);
            }
        }
        return new CurrencyExchangerException(e);
    }
}
