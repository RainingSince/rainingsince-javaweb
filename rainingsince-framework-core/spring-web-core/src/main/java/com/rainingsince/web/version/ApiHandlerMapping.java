package com.rainingsince.web.version;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

public class ApiHandlerMapping extends RequestMappingHandlerMapping {
    private int latestVersion = 1;

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersionRequestCondition condition = buildFrom(AnnotationUtils.findAnnotation(handlerType, ApiVersion.class));
        if (condition != null && condition.getVersion() > latestVersion) {
            ApiVersionRequestCondition.setMaxVersion(condition.getVersion());
        }
        return condition;
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        ApiVersionRequestCondition condition = buildFrom(AnnotationUtils.findAnnotation(method, ApiVersion.class));
        if (condition != null && condition.getVersion() > latestVersion) {
            ApiVersionRequestCondition.setMaxVersion(condition.getVersion());
        }
        return condition;
    }

    private ApiVersionRequestCondition buildFrom(ApiVersion apiVersion) {
        return apiVersion == null ? null : new ApiVersionRequestCondition(apiVersion.value());
    }
}

