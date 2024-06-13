package com.eva.exchange.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HandlerControllerAdvice {

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionResponse handleRecordNotFoundException(final RecordNotFoundException exception, final HttpServletRequest request) {

        return new ExceptionResponse(exception.getMessage(), ErrorType.NO_RECORD, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleValidException(final MethodArgumentNotValidException exception, final HttpServletRequest request) {

        return new ExceptionResponse(exception.getAllErrors().get(0).getDefaultMessage(), ErrorType.VALIDATION, request.getRequestURI());
    }

    @ExceptionHandler(EligibilityException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionResponse handleValidException(final EligibilityException exception, final HttpServletRequest request) {

        return new ExceptionResponse(exception.getMessage(), ErrorType.ELIGIBILITY, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionResponse handleException(final Exception exception, final HttpServletRequest request) {

        return new ExceptionResponse(exception.getMessage(), ErrorType.UNEXPECTED, request.getRequestURI());
    }

}
