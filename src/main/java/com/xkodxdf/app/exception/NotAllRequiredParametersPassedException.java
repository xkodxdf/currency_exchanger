package com.xkodxdf.app.exception;

import com.xkodxdf.app.ErrorMessage;

public class NotAllRequiredParametersPassedException extends InvalidRequestDataException {

    public NotAllRequiredParametersPassedException() {
        super(ErrorMessage.REQUIRED_PARAMS_ERR);
    }

    public NotAllRequiredParametersPassedException(Throwable t) {
        super(ErrorMessage.REQUIRED_PARAMS_ERR, t);
    }

    public NotAllRequiredParametersPassedException(String message) {
        super(message);
    }

    public NotAllRequiredParametersPassedException(String message, Throwable t) {
        super(message, t);
    }
}
