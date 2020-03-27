package com.rainingsince.web.context;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Map<String, RequestContextExecutor> beansOfType = ApplicationProvider.applicationContext.getBeansOfType(RequestContextExecutor.class);
        RequestContext context = new RequestContext();
        for (Map.Entry<String, RequestContextExecutor> entry : beansOfType.entrySet()) {
            entry.getValue().execute(request, context);
        }
        ApplicationProvider.setRequestContext(context);
        request.setAttribute("baseContext", context);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ApplicationProvider.removeRequestContext();
    }
}
