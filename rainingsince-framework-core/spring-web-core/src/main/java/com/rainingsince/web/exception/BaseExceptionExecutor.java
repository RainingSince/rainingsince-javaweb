package com.rainingsince.web.exception;

import com.rainingsince.web.context.ApplicationProvider;
import com.rainingsince.web.response.ResponseBuilder;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class BaseExceptionExecutor implements ExceptionExecutor {

    @Override
    public ResponseEntity execute(Exception e) {
        if (e instanceof BaseErrorException) {
            return ResponseBuilder.error(500, e.getMessage());
        } else {
            Map<String, ExceptionExecutor> beansOfType = ApplicationProvider.applicationContext.getBeansOfType(ExceptionExecutor.class);
            for (Map.Entry<String, ExceptionExecutor> entry : beansOfType.entrySet()) {
                if (!(entry.getValue() instanceof BaseExceptionExecutor)) {
                    ResponseEntity execute = entry.getValue().execute(e);
                    if (execute != null) return execute;
                }
            }
        }
        return ResponseBuilder.error();
    }
}
