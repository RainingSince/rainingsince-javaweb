package com.rainingsince.ali.autoconfigure;

import com.rainingsince.ali.config.AliBaseConfig;
import com.rainingsince.ali.properties.AliProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AliProperties.class})
public class AliAutoConfiguration {

    @Autowired
    private AliProperties aliProperties;

    @Bean
    @ConditionalOnMissingBean
    public AliBaseConfig baseConfig() throws Exception {
        return new AliBaseConfig(aliProperties);
    }


}
