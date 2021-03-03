package com.rainingsince.ali.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rainingsince.ali")
public class AliProperties {

    private String appId;
    private String merchantPrivateKeyPath ;
    private String merchantCertPath ;
    private String alipayCertPath;
    private String alipayRootCertPath ;
    private String notifyUrl;
    private String encryptKey;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMerchantPrivateKeyPath() {
        return merchantPrivateKeyPath;
    }

    public void setMerchantPrivateKeyPath(String merchantPrivateKeyPath) {
        this.merchantPrivateKeyPath = merchantPrivateKeyPath;
    }

    public String getMerchantCertPath() {
        return merchantCertPath;
    }

    public void setMerchantCertPath(String merchantCertPath) {
        this.merchantCertPath = merchantCertPath;
    }

    public String getAlipayCertPath() {
        return alipayCertPath;
    }

    public void setAlipayCertPath(String alipayCertPath) {
        this.alipayCertPath = alipayCertPath;
    }

    public String getAlipayRootCertPath() {
        return alipayRootCertPath;
    }

    public void setAlipayRootCertPath(String alipayRootCertPath) {
        this.alipayRootCertPath = alipayRootCertPath;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }
}
