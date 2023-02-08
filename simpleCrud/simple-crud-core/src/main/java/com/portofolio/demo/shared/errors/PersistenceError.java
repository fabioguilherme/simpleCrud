package com.portofolio.demo.shared.errors;

public class PersistenceError extends RuntimeException {

    public PersistenceError(String message, Throwable cause) {
        super(message, cause);
    }
}
