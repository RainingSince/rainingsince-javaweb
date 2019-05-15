package com.rainingsince.admin.role.error;

import com.rainingsince.web.exception.ErrorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleError implements ErrorEntity {

    ROLE_EXIT(10001, "角色名称已经存在");

    private int code;
    private String message;
}
