package com.rainingsince.admin.permission.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rainingsince.mybatis.entity.BaseData;
import lombok.Data;

@Data
@TableName("sys_permission")
public class Permission extends BaseData {
    private String code;
    private String name;
}
