package com.rainingsince.web.execption;

import com.rainingsince.web.exception.BaseExceptionExecutor;
import com.rainingsince.web.response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @Autowired
    private BaseExceptionExecutor executor;

    @ExceptionHandler(Exception.class)
    public ResponseEntity globalExceptionHandle(NoHandlerFoundException e) {
        return executor.execute(e);
    }
}
