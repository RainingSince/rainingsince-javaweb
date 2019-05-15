package com.rainingsince.mybatis.utils;

import com.rainingsince.web.jasypt.JasyptUtils;

public class EncryptTest {

    public static void main(String[] args) {
        String pwd = JasyptUtils.encryptPwd(args[0], args[2]);
        String user = JasyptUtils.encryptPwd(args[0], args[1]);
        System.out.println(user);
        System.out.println(pwd);
    }
}
