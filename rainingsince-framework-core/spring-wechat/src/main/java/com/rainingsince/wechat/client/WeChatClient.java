package com.rainingsince.wechat.client;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.rainingsince.wechat.dto.WeChatReqEntity;
import com.rainingsince.wechat.properties.WeChatProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

public class WeChatClient {

    private WeChatProperties wechatProperties;

    public WeChatClient(WeChatProperties weChatProperties) {
        this.wechatProperties = weChatProperties;
    }


    public JSONObject getTokenCustom(String wechatId, String secret) {
        String url = " https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + wechatId
                + "&secret="
                + secret;
        String resp = HttpUtil.get(url);
        return JSONObject.parseObject(resp);
    }


    public JSONObject getToken() {
        String url = " https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + wechatProperties.getAppId()
                + "&secret="
                + wechatProperties.getSecret();
        String resp = HttpUtil.get(url);
        return JSONObject.parseObject(resp);
    }


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

    public JSONObject getTokenWithCodeCustomer(String code, String appId, String secret) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +
                appId
                + "&secret=" +
                secret
                + "&code=" +
                code + "&grant_type=authorization_code";

        String resp = HttpUtil.get(url);
        return JSONObject.parseObject(resp);
    }


    public JSONObject getUserInfoWithCode(String code, String token) {
        JSONObject tokenWithCode = getTokenWithCode(code);
        if (StringUtils.isEmpty(tokenWithCode.get("openid"))) {
            return null;
        }
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
                + token
                + "&openid=" +
                tokenWithCode.getString("openid")
                + "&lang=zh_CN";
        String resp = HttpUtil.get(url);
        return JSONObject.parseObject(resp);
    }


    public JSONObject getUserInfoWithCodeCustomer(String code, String token, String appId, String secret) {
        JSONObject tokenWithCode = getTokenWithCodeCustomer(code, appId, secret);
        if (StringUtils.isEmpty(tokenWithCode.get("openid"))) {
            return null;
        }
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
                + token
                + "&openid=" +
                tokenWithCode.getString("openid")
                + "&lang=zh_CN";
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

    public JSONObject getConfigWithCustom(String token) {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";
        String resp = HttpUtil.get(url);
        return JSONObject.parseObject(resp);
    }
}
