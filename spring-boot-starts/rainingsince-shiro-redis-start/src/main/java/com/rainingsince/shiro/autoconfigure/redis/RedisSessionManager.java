package com.rainingsince.shiro.autoconfigure.redis;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import java.io.Serializable;

public class RedisSessionManager extends DefaultWebSessionManager {

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if(sessionKey instanceof WebSessionKey){
            request = ((WebSessionKey)sessionKey).getServletRequest();
        }
        Session session = null;
        if(request != null && sessionId != null){
            session =  (Session) request.getAttribute(sessionId.toString());
        }
        if(session != null){
            return session;
        }
        try{
            session = super.retrieveSession(sessionKey);
        }catch(UnknownSessionException e){

        }
        if(request != null && sessionId != null && session != null){
            request.setAttribute(sessionId.toString(), session);
        }

        return session;
    }

}
