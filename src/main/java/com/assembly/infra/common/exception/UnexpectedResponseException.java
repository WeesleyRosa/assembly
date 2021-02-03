package com.assembly.infra.common.exception;

public class UnexpectedResponseException extends RuntimeException {

    public UnexpectedResponseException() {
        super("Unexpected Response");
    }

    public UnexpectedResponseException(String message) {
        super(message);
    }

}