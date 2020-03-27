package com.rainingsince.admin.autoconfigure;

import com.rainingsince.admin.ScanConfiguration;
import com.rainingsince.admin.plugs.MyMetaObjectHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ScanConfiguration.class)
public class AdminAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MyMetaObjectHandler metaObjectHandler() {
        return new MyMetaObjectHandler();
    }

}
