package com.rainingsince.admin.userRole.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rainingsince.mybatis.entity.BaseData;
import lombok.Data;

@Data
@TableName("sys_user_role")
public class UserRole extends BaseData {

    private String roleId;
    private String userId;

}
