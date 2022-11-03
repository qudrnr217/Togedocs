package com.togedocs.backend.api.exception;

public class IdNotFoundException extends Exception {
    public IdNotFoundException(String key) {
        super("\"" + key + "\" not found");
    }
}
