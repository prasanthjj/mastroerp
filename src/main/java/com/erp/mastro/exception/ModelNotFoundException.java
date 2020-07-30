package com.erp.mastro.exception;

public class ModelNotFoundException extends Exception {

    public ModelNotFoundException() {

    }

    public ModelNotFoundException(String message) {
        super(message);
    }

    public ModelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
