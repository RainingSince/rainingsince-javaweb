package com.rainingsince.web.autoconfigure;

import com.rainingsince.web.context.ApplicationProvider;
import com.rainingsince.web.context.BaseRequestContextExecutor;
import com.rainingsince.web.context.ContextInterceptor;
import com.rainingsince.web.exception.BaseExceptionExecutor;
import com.rainingsince.web.execption.GlobalExceptionHandler;
import com.rainingsince.web.jwt.TokenService;
import com.rainingsince.web.version.ApiHandlerMapping;
import com.rainingsince.web.xss.XssFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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
@EnableConfigurationProperties({ProjectWebProperties.class})
@EnableTransactionManagement
@EnableScheduling
@EnableCaching
public class WebAutoConfiguration implements WebMvcConfigurer, WebMvcRegistrations {

    @Value("${spring.profiles.active}")
    private String profiles;

    @Autowired
    private ProjectWebProperties projectProperties;

    @Bean
    @ConditionalOnMissingBean
    public TokenService tokenService(ProjectWebProperties properties) {
        if (StringUtils.isEmpty(profiles)) profiles = "project";
        return new TokenService(properties.getTokenKey(), profiles);
    }

    @Bean
    @ConditionalOnMissingBean
    public BaseRequestContextExecutor baseRequestContextExecutor(){
        return new BaseRequestContextExecutor();
    }

    @Bean
    @ConditionalOnMissingBean
    public BaseExceptionExecutor baseExceptionExecutor(){
        return new BaseExceptionExecutor();
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
        registry.addInterceptor(new ContextInterceptor()).addPathPatterns("/**");
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
