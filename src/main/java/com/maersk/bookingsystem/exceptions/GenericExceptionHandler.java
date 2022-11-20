package com.maersk.bookingsystem.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GenericExceptionHandler {

    public static final String FIELD_SEPARATOR = ": ";
    public static final String CODE = "Code";
    public static final String DESCRIPTION = "Description";
    public static final String PROBLEM_PROCESSING_YOUR_REQUEST = "Sorry there was a problem processing your request";

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<List<String>> handleException(WebExchangeBindException e) {
        var errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + FIELD_SEPARATOR + error.getDefaultMessage())
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {

        log.info("Internal Server Error - [{}]", e.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(CODE, HttpStatus.INTERNAL_SERVER_ERROR);
        body.put(DESCRIPTION, PROBLEM_PROCESSING_YOUR_REQUEST);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
