package com.rainingsince.shiro.autoconfigure.redis;

import org.apache.shiro.dao.DataAccessException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class RedisSessionDao extends AbstractSessionDAO {

    public static final String SESSION_PREFIX = "shiro_session:";
    public static final int DEFAILT_TIME_OUT = 30;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private int timeout = DEFAILT_TIME_OUT;

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable id = generateSessionId(session);
        assignSessionId(session, id);
        saveSession(session);
        return id;
    }

    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    public void delete(Session session) {
        if(session == null || session.getId() == null){
            return;
        }
        redisTemplate.delete(getKey(session.getId()));
    }

    public Collection<Session> getActiveSessions() {
        Set<String> keys = keys();
        Set<Session> sessions = new HashSet<Session>();
        if(keys.size() == 0){
            return sessions;
        }
        for (String id : keys) {
            Session _session = getSession(id);
            if(_session == null){
                continue;
            }
            sessions.add(_session);
        }
        return sessions;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            return null;
        }
        return getSession(sessionId);
    }

    private static String getKey(Serializable id){
        return SESSION_PREFIX + id.toString();
    }

    private void saveSession(Session session){
        if(session != null && session.getId() != null){
            Serializable id = session.getId();
            redisTemplate.opsForValue().set(getKey(id), session, timeout, TimeUnit.MINUTES);
        }
    }

    private Session getSession(Serializable id){
        return (Session)redisTemplate.boundValueOps(getKey(id)).get();
    }

    private Set<String> keys(){
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<String> binaryKeys = new HashSet<String>();
                Cursor<byte[]> cursor = connection.scan( new ScanOptions.ScanOptionsBuilder().match(SESSION_PREFIX + "*").count(1000).build());
                while (cursor.hasNext()) {
                    binaryKeys.add(new String(cursor.next()));
                }
                connection.close();
                return binaryKeys;
            }
        });
    }
}
