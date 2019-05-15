package com.rainingsince.web.exception;


public class MyException extends RuntimeException {

    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
    }
}
