package com.rainingsince.web.context;

import com.rainingsince.web.jwt.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseRequestContextExecutor implements RequestContextExecutor {

    @Override
    public RequestContext execute(HttpServletRequest request,RequestContext context) {
        context.setPath(request.getRequestURI());
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
