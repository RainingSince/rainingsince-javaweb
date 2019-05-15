package com.rainingsince.web.response;

import com.rainingsince.web.exception.BaseEntity;
import com.rainingsince.web.exception.ErrorEntity;
import com.rainingsince.web.exception.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    public static <T> ResponseEntity ok(T entity) {
        return ok(HttpStatus.OK, entity);
    }

    public static <T> ResponseEntity ok(HttpStatus httpStatus, T entity) {
        return new ResponseEntity<T>(entity, httpStatus);
    }


    public static ResponseEntity error(ErrorEntity errorEntity) {
        return ok(HttpStatus.SERVICE_UNAVAILABLE, new ResponseError(errorEntity));
    }

    public static ResponseEntity error(ErrorEntity errorEntity, HttpStatus httpStatus) {
        return ok(httpStatus, new ResponseError(errorEntity));
    }

    public static ResponseEntity error(int code, String message) {
        return ok(HttpStatus.SERVICE_UNAVAILABLE, new ResponseError(code, message));
    }

    public static ResponseEntity error() {
        return error(BaseEntity.SERVER_ERROR);
    }

}
