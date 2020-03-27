package com.rainingsince.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SinceShiroFilter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isEmpty(token)){
            token = (String) SecurityUtils.getSubject().getSession().getAttribute(HttpHeaders.AUTHORIZATION);
        }
        return new JwtToken(token);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return executeLogin(servletRequest, servletResponse);
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,HEAD,POST,PUT,PATCH,DELETE,OPTIONS,TRACE");
        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        if (RequestMethod.OPTIONS.name().equals(WebUtils.toHttp(request).getMethod())) {
            httpResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
