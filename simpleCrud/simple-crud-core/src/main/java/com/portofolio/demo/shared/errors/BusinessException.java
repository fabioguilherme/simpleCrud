package com.portofolio.demo.shared.errors;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
