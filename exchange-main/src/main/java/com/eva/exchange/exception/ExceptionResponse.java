package com.eva.exchange.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private String errorMessage;
    private ErrorType errorType;
    private String requestedURI;

    public void callerURL(final String requestedURI) {
        this.requestedURI = requestedURI;
    }
}
