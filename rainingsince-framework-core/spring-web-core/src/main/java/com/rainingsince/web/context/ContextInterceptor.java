package com.rainingsince.web.context;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.Oneway;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ContextInterceptor<T> implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        T t = formatRequestContext(request);
        ApplicationProvider.setRequestContext(t);
        request.setAttribute("baseContext", t);
        return true;
    }

    public abstract T formatRequestContext(HttpServletRequest request);

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ApplicationProvider.removeRequestContext();
    }
}
