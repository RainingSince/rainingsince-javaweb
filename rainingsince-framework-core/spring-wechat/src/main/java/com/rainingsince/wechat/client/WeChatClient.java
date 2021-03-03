package com.rainingsince.wechat.client;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.rainingsince.wechat.dto.WeChatReqEntity;
import com.rainingsince.wechat.properties.WeChatProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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


}
