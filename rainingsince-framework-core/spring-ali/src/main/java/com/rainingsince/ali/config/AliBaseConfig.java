package com.rainingsince.ali.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.rainingsince.ali.properties.AliProperties;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class AliBaseConfig extends Config {

    private AliProperties aliProperties;

    public AliBaseConfig(AliProperties aliProperties) throws Exception {
        this.aliProperties = aliProperties;
        this.protocol = "https";
        this.gatewayHost = "openapi.alipay.com";
        this.signType = "RSA2";
        this.appId = aliProperties.getAppId();
        File file = new ClassPathResource(aliProperties.getMerchantPrivateKeyPath())
                .getFile();
        InputStream certStream = new FileInputStream(file);
        byte[] certData = new byte[(int) file.length()];
        certStream.read(certData);
        certStream.close();
        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        this.merchantPrivateKey = Arrays.toString(certData);
        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
        this.merchantCertPath = aliProperties.getMerchantCertPath();
        this.alipayCertPath = aliProperties.getAlipayCertPath();
        this.alipayRootCertPath = aliProperties.getAlipayRootCertPath();
        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        // config.alipayPublicKey = "<-- 请填写您的支付宝公钥，例如：MIIBIjANBg... -->";
        //可设置异步通知接收服务地址（可选）
        this.notifyUrl = aliProperties.getNotifyUrl();
        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
        this.encryptKey = aliProperties.getEncryptKey();
        Factory.setOptions(this);
    }

}
