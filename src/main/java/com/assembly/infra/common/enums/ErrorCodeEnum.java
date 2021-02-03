package com.assembly.infra.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCodeEnum {

    INVALID_REQUEST_BODY("INVALID_REQUEST_BODY"),
    DATA_NOT_FOUND("DATA_NOT_FOUND"),
    CLIENT_REQUEST_ERROR("CLIENT_REQUEST_ERROR"),
    SERVICE_UNHANDLED_ERROR("CLIENT_REQUEST_ERROR"),
    AGENDA_NOT_FOUND("CLIENT_REQUEST_ERROR"),
    USER_UNABLE_TO_VOTE("CLIENT_REQUEST_ERROR");

    private String value;

    ErrorCodeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
