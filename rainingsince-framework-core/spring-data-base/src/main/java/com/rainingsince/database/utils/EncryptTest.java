package com.rainingsince.database.utils;

import com.rainingsince.database.jasypt.JasyptUtils;

public class EncryptTest {
    /*
    *  args[0] 加密密钥
    *  args[1] 加密用户名
    *  args[2] 加密密码
    *  输出加密有的 用户名和密码
    * */
    public static void main(String[] args) {
        String pwd = JasyptUtils.encryptPwd(args[0], args[2]);
        String user = JasyptUtils.encryptPwd(args[0], args[1]);
        System.out.println(user);
        System.out.println(pwd);
    }
}
