package com.rainingsince.admin.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rainingsince.admin.entity.BaseData;
import lombok.Data;
import java.util.List;

@Data
@TableName("sys_user")
public class User extends BaseData {
    private String account;
    private String password;
    private String phone;
    private String name;

    @TableField(exist = false)
    private List<String> roleList;
}
