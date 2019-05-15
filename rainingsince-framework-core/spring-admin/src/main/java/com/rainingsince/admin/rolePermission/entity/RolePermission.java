package com.rainingsince.admin.rolePermission.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rainingsince.mybatis.entity.BaseData;
import lombok.Data;

@Data
@TableName("sys_role_permission")
public class RolePermission extends BaseData {
    private String roleId;
    private String permissionId;
}
