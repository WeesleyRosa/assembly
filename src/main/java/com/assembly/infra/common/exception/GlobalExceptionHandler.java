package com.assembly.infra.common.exception;

import com.assembly.infra.common.enums.ErrorCodeEnum;
import com.assembly.infra.common.web.response.ErrorResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AgendaException.class)
    public ResponseEntity<ErrorResponse> handleAgendaException(AgendaException ex) {
        log.error("AgendaException - HttpStatus: {} - Error Code: {} - Message: {}", ex.getHttpStatus(), ex.getErrorCode(), ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(UserVoteException.class)
    public ResponseEntity<ErrorResponse> handleUserCpfException(AgendaException ex) {
        log.error("VoteException - HttpStatus: {} - Error Code: {} - Message: {}", ex.getHttpStatus(), ex.getErrorCode(), ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeign(FeignException ex) {
        log.error("FeignException - Error calling client: {}", ex.getMessage());
        log.error("FeignException - Response error: {}", ex.contentUTF8());

        String message = String.format("Error requesting. Method:  %s - URL: %s", ex.request().httpMethod().name(), ex.request().url());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCodeEnum.CLIENT_REQUEST_ERROR, message);
        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Exception - Unhandled exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCodeEnum.SERVICE_UNHANDLED_ERROR, "Unhandled service exception");
        return ResponseEntity.status(500).body(errorResponse);
    }
}
