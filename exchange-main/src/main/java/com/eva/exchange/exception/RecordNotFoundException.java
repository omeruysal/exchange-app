package com.eva.exchange.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(final String message) {
        super(message);
    }
}
