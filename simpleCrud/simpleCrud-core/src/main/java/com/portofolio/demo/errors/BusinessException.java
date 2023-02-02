package com.portofolio.demo.errors;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
