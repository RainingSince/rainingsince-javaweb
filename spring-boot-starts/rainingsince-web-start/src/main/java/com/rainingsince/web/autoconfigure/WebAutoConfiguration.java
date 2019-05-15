package com.rainingsince.web.autoconfigure;

import com.rainingsince.web.context.ApplicationProvider;
import com.rainingsince.web.context.BaseContextInterceptor;
import com.rainingsince.web.exception.GlobalExceptionHandler;
import com.rainingsince.web.jwt.TokenService;
import com.rainingsince.web.version.ApiHandlerMapping;
import com.rainingsince.web.xss.XssFilter;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@Import({GlobalExceptionHandler.class, ApplicationProvider.class})
@EnableConfigurationProperties({ProjectProperties.class})
@EnableEncryptableProperties
@EnableTransactionManagement
@EnableScheduling
public class WebAutoConfiguration implements WebMvcConfigurer, WebMvcRegistrations {

    @Value("${spring.profiles.active}")
    private String profiles;

    @Autowired
    private ProjectProperties projectProperties;

    @Bean
    @ConditionalOnMissingBean
    public TokenService tokenService(ProjectProperties properties) {
        if (StringUtils.isEmpty(profiles)) profiles = "project";
        return new TokenService(properties.getTokenKey(), profiles);
    }


    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        if (!projectProperties.isVersionEnable()) return null;
        ApiHandlerMapping handlerMapping = new ApiHandlerMapping();
        handlerMapping.setOrder(0);
        return handlerMapping;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BaseContextInterceptor()).addPathPatterns("/**");
    }


    @Bean
    @ConditionalOnMissingBean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    @Bean
    public FilterRegistrationBean<XssFilter> testFilterRegistration() {
        FilterRegistrationBean<XssFilter> registration =
                new FilterRegistrationBean<>(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        return registration;
    }

}
