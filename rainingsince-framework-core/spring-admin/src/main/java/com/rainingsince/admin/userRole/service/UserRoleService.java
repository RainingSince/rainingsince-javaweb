package com.rainingsince.admin.userRole.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainingsince.admin.userRole.entity.UserRole;
import com.rainingsince.admin.userRole.mapper.UserRoleMapper;
import com.rainingsince.web.context.ApplicationProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserRoleService extends
        ServiceImpl<UserRoleMapper, UserRole> {


    @Override
    public boolean save(UserRole entity) {
        entity.setId(IdWorker.getIdStr());
        return super.save(entity);
    }

    public Long saveUserRole(Serializable userId, List<String> roles) {
        boolean isAdmin = roles.stream().anyMatch("0"::equals);
        if ("0".equals(userId) || isAdmin) return 0L;
        return roles.stream().map(id -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(id);
            userRole.setUserId((String) userId);
            return save(userRole);
        }).count();
    }

    public List<String> listRolesByUserId(Serializable userId) {
        return list(new QueryWrapper<UserRole>()
                .eq("user_id", userId))
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
    }
}
