package com.rainingsince.admin.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rainingsince.admin.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
