package com.rainingsince.web.exception;


public class BaseErrorException extends RuntimeException {

    public BaseErrorException() {
        super();
    }

    public BaseErrorException(String message) {
        super(message);
    }
}
