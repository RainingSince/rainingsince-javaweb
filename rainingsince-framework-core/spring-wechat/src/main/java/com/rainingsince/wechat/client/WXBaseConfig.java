package com.rainingsince.wechat.client;

import com.rainingsince.wechat.properties.WeChatProperties;
import com.rainingsince.wechat.utils.ResourceUtil;
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
            File file = ResourceUtil
                    .getResourceWithPath(weChatProperties.getPrivateKeyPath())
                    .getFile();
            InputStream certStream = new FileInputStream(file);
            this.certData = new byte[(int) file.length()];
            certStream.read(this.certData);
            certStream.close();
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
        return weChatProperties.getApiV3Key();
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
