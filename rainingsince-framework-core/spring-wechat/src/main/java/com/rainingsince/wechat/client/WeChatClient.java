package com.rainingsince.wechat.client;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.rainingsince.wechat.dto.WeChatReqEntity;
import com.rainingsince.wechat.properties.WeChatProperties;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;


@Component
public class WeChatClient {

    @Autowired
    private WeChatProperties wechatProperties;


    public String formatRequestUrl(WeChatReqEntity entity) {
        return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                wechatProperties.getAppId() +
                "&redirect_uri=" +
                entity.getRedirectUrl() +
                "&response_type=code&scope=" +
                entity.getScope() +
                "&state=" +
                entity.getState() +
                "#wechat_redirect";
    }

    public JSONObject getTokenWithCode(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +
                wechatProperties.getAppId()
                + "&secret=" +
                wechatProperties.getSecret()
                + "&code=" +
                code + "&grant_type=authorization_code";

        String resp = HttpUtil.get(url);
        return JSONObject.parseObject(resp);
    }

    public JSONObject getUserInfoWithToken(String token, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" +
                token
                + "&openid=" +
                openId
                + "&lang=zh_CN";
        String resp = HttpUtil.get(url);
        return JSONObject.parseObject(resp);
    }

    public JSONObject getUserInfoWithCode(String code) {
        JSONObject tokenWithCode = this.getTokenWithCode(code);
        if (!StringUtils.isEmpty(tokenWithCode.get("openid"))) {
            return getUserInfoWithToken(tokenWithCode.getString("access_token"), tokenWithCode.getString("openid"));
        }
        return null;
    }


    public void init() throws UnsupportedEncodingException {
        String mchId = "";
        String privateKey = "管理后台下载";
        String mchSerialNo = "管理后台下载";
        String apiV3Key = "管理后台中设置";

        // 加载商户私钥（privateKey：私钥字符串）
        PrivateKey merchantPrivateKey = PemUtil
                .loadPrivateKey(new ByteArrayInputStream(privateKey.getBytes(StandardCharsets.UTF_8)));

        // 加载平台证书（mchId：商户号,mchSerialNo：商户证书序列号,apiV3Key：V3秘钥）
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)), apiV3Key.getBytes(StandardCharsets.UTF_8));

        // 初始化httpClient
        HttpClient httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier)).build();


    }


}
