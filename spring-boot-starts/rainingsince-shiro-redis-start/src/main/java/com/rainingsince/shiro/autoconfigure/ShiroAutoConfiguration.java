package com.rainingsince.shiro.autoconfigure;

import com.rainingsince.shiro.ShiroRealm;
import com.rainingsince.shiro.SinceShiroFilter;
import com.rainingsince.shiro.autoconfigure.redis.RedisCacheManager;
import com.rainingsince.shiro.autoconfigure.redis.RedisSessionDao;
import com.rainingsince.shiro.autoconfigure.redis.RedisSessionManager;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@Import({RedisSessionManager.class, RedisCacheManager.class, RedisSessionDao.class})
public class ShiroAutoConfiguration {

    @Bean("securityManager")
    @ConditionalOnMissingBean
    public SessionsSecurityManager securityManager(ShiroRealm realm, RedisCacheManager redisCacheManager, RedisSessionManager redisSessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(redisCacheManager);
        securityManager.setSessionManager(redisSessionManager);
        return securityManager;
    }


    @Bean("shiroFilter")
    @ConditionalOnMissingBean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2", new SinceShiroFilter());
        shiroFilter.setFilters(filters);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/**", "oauth2");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilter;
    }

    @Bean
    @ConditionalOnMissingBean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisSessionManager redisSessionManager(RedisSessionDao redisSessionDao) {
        RedisSessionManager redisSessionManager = new RedisSessionManager();
        redisSessionManager.setSessionDAO(redisSessionDao);
        return redisSessionManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisSessionDao redisSessionDao() {
        return new RedisSessionDao();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

}
