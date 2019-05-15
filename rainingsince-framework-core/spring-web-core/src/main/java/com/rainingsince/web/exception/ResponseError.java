package com.rainingsince.web.exception;

import com.rainingsince.web.context.ApplicationProvider;
import com.rainingsince.web.context.RequestContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseError extends ResponseEntity {
    private int code;
    private String message;
    private String path;
    private long timestamp;

    public ResponseError() {
        super(HttpStatus.OK);
        this.timestamp = System.currentTimeMillis();
        this.path = ApplicationProvider.getRequestContext(RequestContext.class).getPath();
    }

    public ResponseError(ErrorEntity errorEntity) {
        this();
        this.code = errorEntity.getCode();
        this.message = errorEntity.getMessage();
    }

    public ResponseError(int code, String message) {
        this();
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
