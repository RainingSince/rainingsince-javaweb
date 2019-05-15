package com.rainingsince.admin.permission.error;

import com.rainingsince.web.exception.ErrorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionError implements ErrorEntity {
    PERMISSION_EXIT(1001, "权限已经存在");

    private int code;
    private String message;

}
