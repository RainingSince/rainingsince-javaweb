package com.rainingsince.web.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApplicationProvider implements ApplicationContextAware {
    public static ConfigurableApplicationContext applicationContext;
    private static ThreadLocal<Object> requestContext = new ThreadLocal<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationProvider.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    public static <T> T getRequestContext(Class<T> tClass) {
        return (T) requestContext.get();
    }

    public static void removeRequestContext() {
        requestContext.remove();
    }

    public static void setRequestContext(Object context) {
        requestContext.set(context);
    }
}
