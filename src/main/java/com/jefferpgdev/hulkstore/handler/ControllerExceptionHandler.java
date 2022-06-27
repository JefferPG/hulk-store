package com.jefferpgdev.hulkstore.handler;

import com.jefferpgdev.hulkstore.controller.dto.ResponseError;
import com.jefferpgdev.hulkstore.exception.InvalidDataException;
import com.jefferpgdev.hulkstore.exception.OperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResponseError> noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException ex) {
        return handleErrorResponse(ex, NOT_FOUND.value(), "NoHandlerFound");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseError> httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest req, HttpRequestMethodNotSupportedException ex) {
        return handleErrorResponse(ex, BAD_REQUEST.value(), "HttpRequestMethodNotSupported");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseError> httpMessageNotReadableExceptionHandler(HttpServletRequest req, HttpMessageNotReadableException ex) {
        return handleErrorResponse(ex, BAD_REQUEST.value(), "HttpMessageNotReadable");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseError> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        return handleErrorResponse(ex, BAD_REQUEST.value(), "MissingServletRequestParameter");
    }

    @ExceptionHandler(ConversionFailedException.class)
    protected ResponseEntity<ResponseError> conversionFailedExceptionHandler(ConversionFailedException ex) {
        return handleErrorResponse(ex, BAD_REQUEST.value(), "ConversionFailed");
    }

    @ExceptionHandler(InvalidDataException.class)
    protected ResponseEntity<ResponseError> invalidDataExceptionHandler(InvalidDataException ex) {
        return handleErrorResponse(ex, BAD_REQUEST.value(), "InvalidData");
    }

    @ExceptionHandler(OperationException.class)
    protected ResponseEntity<ResponseError> operationExceptionHandler(OperationException ex) {
        return handleErrorResponse(ex, BAD_REQUEST.value(), "Operation");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ResponseError> sataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex) {
        return handleErrorResponse(ex, BAD_REQUEST.value(), "DataIntegrityViolation");
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseError> handleUnknownExceptionHandler(Exception ex) {
        return handleErrorResponse(ex, INTERNAL_SERVER_ERROR.value(), "InternalError");
    }

    private ResponseEntity<ResponseError> handleErrorResponse(Exception e, Integer status, String type) {
        log.error(String.format("%s - %s", e.getMessage(), getFullStackTrace(e)));
        return ResponseEntity
                .status(status)
                .body(ResponseError
                        .builder()
                        .status(status)
                        .type(type)
                        .message(e.getMessage())
                        .build());
    }
}
