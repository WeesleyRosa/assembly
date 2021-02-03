package com.assembly.infra.common.exception;

public class ClientCommunicationException extends RuntimeException {

    public ClientCommunicationException() {
        super();
    }

    public ClientCommunicationException(String message) {
        super(message);
    }

}
