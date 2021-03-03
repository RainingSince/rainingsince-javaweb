package com.rainingsince.wechat.autoconfigure;

import com.rainingsince.wechat.client.WXBaseConfig;
import com.rainingsince.wechat.client.WeChatClient;
import com.rainingsince.wechat.properties.WeChatProperties;
import com.rainingsince.wechat.v2.sdk.WXPayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({WeChatProperties.class})
public class WeChatAutoConfiguration {


    @Autowired
    private WeChatProperties weChatProperties;

    @Bean
    @ConditionalOnMissingBean
    public WeChatClient weChatClient() {
        return new WeChatClient(weChatProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public WXPayRequest wxPayRequest() throws Exception {
        return new WXPayRequest(new WXBaseConfig(weChatProperties));
    }

}
