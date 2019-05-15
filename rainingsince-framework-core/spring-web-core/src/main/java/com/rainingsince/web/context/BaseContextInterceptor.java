package com.rainingsince.web.context;

import com.rainingsince.web.jwt.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseContextInterceptor extends ContextInterceptor<RequestContext> {

    @Override
    public RequestContext formatRequestContext(HttpServletRequest request) {
        RequestContext context = new RequestContext();
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.isEmpty(token)) {
            TokenService bean =
                    ApplicationProvider.applicationContext.getBean(TokenService.class);
            Claims claims = bean.validateToken(token);
            if (claims != null && !StringUtils.isEmpty(claims.getAudience())) {
                request.setAttribute("userId", claims.getAudience());
                context.setUserId(claims.getAudience());
            }

        }
        return context;
    }
}
