package com.rainingsince.admin.auth.error;

import com.rainingsince.web.exception.ErrorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthError implements ErrorEntity {

    USER_NOT_EXIT(10001, "用户名不存在"),
    PASSWORD_ERROR(10002, "密码错误");

    private int code;
    private String message;

}
