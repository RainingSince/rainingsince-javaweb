package com.rainingsince.wechat.client;

import cn.hutool.core.io.resource.ResourceUtil;
import com.rainingsince.wechat.properties.WeChatProperties;
import com.rainingsince.wechat.v2.sdk.IWXPayDomain;
import com.rainingsince.wechat.v2.sdk.WXPayConfig;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WXBaseConfig extends WXPayConfig {

    private WeChatProperties weChatProperties;

    private byte[] certData;

    public WXBaseConfig(WeChatProperties weChatProperties) throws Exception {
        this.weChatProperties = weChatProperties;
        if (!StringUtils.isEmpty(weChatProperties.getPrivateKeyPath())) {
            this.certData = ResourceUtil.readBytes(weChatProperties.getPrivateKeyPath());
        }

    }

    @Override
    public String getAppID() {
        return weChatProperties.getAppId();
    }

    @Override
    public String getMchID() {
        return weChatProperties.getMchId();
    }

    @Override
    public String getKey() {
        return weChatProperties.getApiKey();
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo("api.mch.weixin.qq.com", true);
            }
        };
    }
}
