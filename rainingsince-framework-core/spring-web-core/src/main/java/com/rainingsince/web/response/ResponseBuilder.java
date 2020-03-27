package com.rainingsince.web.response;

import com.rainingsince.web.exception.BaseErrorEntity;
import com.rainingsince.web.exception.ErrorEntity;
import com.rainingsince.web.exception.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    public static <T> ResponseEntity success(T entity) {
        return success(HttpStatus.OK, entity);
    }

    public static <T> ResponseEntity success(HttpStatus httpStatus, T entity) {
        return new ResponseEntity<T>(entity, httpStatus);
    }


    public static ResponseEntity error(ErrorEntity errorEntity) {
        return success(HttpStatus.SERVICE_UNAVAILABLE, new ResponseError(errorEntity));
    }

    public static ResponseEntity error(ErrorEntity errorEntity, HttpStatus httpStatus) {
        return success(httpStatus, new ResponseError(errorEntity));
    }

    public static ResponseEntity error(int code, String message) {
        return success(HttpStatus.SERVICE_UNAVAILABLE, new ResponseError(code, message));
    }

    public static ResponseEntity error(int code, String message,HttpStatus httpStatus) {
        return success(httpStatus, new ResponseError(code, message));
    }

    public static ResponseEntity error() {
        return error(BaseErrorEntity.SERVER_ERROR);
    }

}
