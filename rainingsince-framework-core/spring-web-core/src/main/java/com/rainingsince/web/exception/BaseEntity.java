package com.rainingsince.web.exception;

public enum BaseEntity implements ErrorEntity {

    SERVER_ERROR(500, "服务器异常");


    private int code;
    private String message;

    BaseEntity(int code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
