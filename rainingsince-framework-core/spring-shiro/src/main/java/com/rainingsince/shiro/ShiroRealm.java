package com.rainingsince.shiro;

import com.rainingsince.web.context.ApplicationProvider;
import com.rainingsince.web.exception.BaseErrorException;
import com.rainingsince.web.jwt.TokenService;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


public abstract class ShiroRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return getCustomAuthorizationInfo(principalCollection);
    }

    public abstract AuthorizationInfo getCustomAuthorizationInfo(PrincipalCollection principalCollection);

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        if (authenticationToken.getPrincipal() != null) {
            String token = (String) authenticationToken.getPrincipal();
            TokenService bean = ApplicationProvider.applicationContext.getBean(TokenService.class);
            Claims claims = bean.validateToken(token);
            if (claims != null) {
                if (claims.getAudience() == null) {
                    throw new BaseErrorException();
                }
                return new SimpleAuthenticationInfo(claims.getAudience(), authenticationToken.getCredentials().toString(),
                        getName());
            }
        }
        return null;
    }
}
