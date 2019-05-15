package com.rainingsince.admin.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainingsince.admin.auth.entity.Auth;
import com.rainingsince.admin.auth.error.AuthError;
import com.rainingsince.admin.shiro.MyShiroReaml;
import com.rainingsince.admin.user.entity.User;
import com.rainingsince.admin.user.service.UserService;
import com.rainingsince.shiro.JwtToken;
import com.rainingsince.shiro.ShiroRealm;
import com.rainingsince.web.context.ApplicationProvider;
import com.rainingsince.web.jwt.TokenService;
import com.rainingsince.web.response.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthService {

    TokenService tokenService;

    UserService userService;


    public ResponseEntity login(User entity) {
        User user = userService.getOne(new QueryWrapper<User>()
                .eq("account", entity.getAccount()));

        if (user == null) return ResponseBuilder
                .error(AuthError.USER_NOT_EXIT);

        if (!user.getPassword().equals(entity.getPassword()))
            return ResponseBuilder.error(AuthError.PASSWORD_ERROR);

        String token = tokenService.createToken(user.getId(), -1);
        Auth login = new Auth(user.getName(), token);
        Subject subject = SecurityUtils.getSubject();
        subject.login(new JwtToken(token));
        return ResponseBuilder.ok(login);
    }


    public int logout() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        if (principalCollection != null) {
            ApplicationProvider
                    .applicationContext
                    .getBean(ShiroRealm.class)
                    .getAuthorizationCache()
                    .remove(principalCollection);
        }
        return 0;
    }


    public Collection<String> permissions() {
        Cache<Object, AuthorizationInfo> cache = ApplicationProvider
                .applicationContext
                .getBean(ShiroRealm.class)
                .getAuthorizationCache();
        AuthorizationInfo authorizationInfo = cache
                .get(SecurityUtils.getSubject().getPrincipals());
        if (authorizationInfo == null) {
            SecurityUtils.getSubject()
                    .isPermitted("permissions");
            authorizationInfo = cache
                    .get(SecurityUtils.getSubject()
                            .getPrincipals());
        }
        return authorizationInfo == null ?
                Collections.emptyList()
                : authorizationInfo.getStringPermissions();
    }
}
