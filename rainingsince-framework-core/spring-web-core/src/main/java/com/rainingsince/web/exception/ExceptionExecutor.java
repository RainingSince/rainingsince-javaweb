package com.rainingsince.web.exception;

import org.springframework.http.ResponseEntity;

public interface ExceptionExecutor {
    ResponseEntity execute(Exception e);
}
