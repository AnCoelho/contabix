package com.projeto.contabix.exception;

import lombok.Getter;

public class NotFoundException extends RuntimeException {

    @Getter
    private String code;

    private String message;

    public NotFoundException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
