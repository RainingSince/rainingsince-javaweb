package com.rainingsince.admin.user.error;

import com.rainingsince.web.exception.ErrorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserError implements ErrorEntity {
    USER_NAME_EXIT(10001, "用户名已存在");
    private int code;
    private String message;

}
