package com.rainingsince.admin.role.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rainingsince.admin.permission.entity.Permission;
import com.rainingsince.mybatis.entity.BaseData;
import lombok.Data;

import java.util.List;

@Data
@TableName("sys_role")
public class Role extends BaseData {
    private String name;

    @TableField(exist = false)
    private List<String> permissionList;

}
