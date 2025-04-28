package com.xkodxdf.app;

public class ErrorResponse {

    private final int statusCode;
    private final ErrorMessage message;


    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = new ErrorMessage(message);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public ErrorMessage getMessage() {
        return message;
    }

    public record ErrorMessage(String message) {
    }
}
