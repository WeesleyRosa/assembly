package com.assembly.infra.common.exception;

import com.assembly.infra.common.enums.ErrorCodeEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AgendaException extends RuntimeException {

    private HttpStatus httpStatus;
    private ErrorCodeEnum errorCode;

    public AgendaException(HttpStatus httpStatus, ErrorCodeEnum errorCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
