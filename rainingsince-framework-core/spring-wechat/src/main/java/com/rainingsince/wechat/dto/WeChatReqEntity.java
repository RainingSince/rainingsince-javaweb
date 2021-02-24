package com.rainingsince.wechat.dto;

import lombok.Data;

@Data
public class WeChatReqEntity {
    private String code;
    private String redirectUrl;
    private String scope;
    private String state;
    private String token;
    private String openId;
}


