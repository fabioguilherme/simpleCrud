package com.portofolio.demo.models.json;

public class ErrorMessage {
    private final String message;

    private ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    public static ErrorMessage createErrorWithMessage(String message) {
        return new ErrorMessage(message);
    }
}

