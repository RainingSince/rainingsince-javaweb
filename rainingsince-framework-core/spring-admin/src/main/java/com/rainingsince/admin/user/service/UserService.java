package com.rainingsince.admin.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainingsince.admin.role.entity.Role;
import com.rainingsince.admin.role.service.RoleService;
import com.rainingsince.admin.user.entity.User;
import com.rainingsince.admin.user.error.UserError;
import com.rainingsince.admin.user.mapper.UserMapper;
import com.rainingsince.admin.userRole.entity.UserRole;
import com.rainingsince.admin.userRole.service.UserRoleService;
import com.rainingsince.web.response.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService extends
        ServiceImpl<UserMapper, User> {

    private UserRoleService userRoleService;
    private RoleService roleService;

    @Override
    public boolean save(User entity) {
        entity.setId(IdWorker.getIdStr());
        return super.save(entity);
    }

    @Override
    public User getById(Serializable id) {
        User user = super.getById(id);
        List<String> roles = null;
        if ("0".equals(id)) {
            roles = roleService.list()
                    .stream()
                    .map(Role::getId)
                    .collect(Collectors.toList());
        } else {
            roles = userRoleService.listRolesByUserId(id);
        }
        user.setRoleList(roles);
        return user;
    }

    public IPage<User> pages(User user) {
        return page(user.toPage(), new QueryWrapper<>(user));
    }

    public ResponseEntity saveNotExit(User entity) {
        if (isExit(entity)) return ResponseBuilder.error(UserError.USER_NAME_EXIT);
        return ResponseBuilder.ok(save(entity));
    }

    public Long saveUserRoles(String userId, List<String> roleIds) {
        return userRoleService.saveUserRole(userId, roleIds);
    }

    public ResponseEntity updateNotExit(User entity) {
        if (isExit(entity)) return ResponseBuilder.error(UserError.USER_NAME_EXIT);
        return ResponseBuilder.ok(updateById(entity));
    }

    @Override
    public boolean removeById(Serializable id) {
        userRoleService.remove(new QueryWrapper<UserRole>()
                .eq("user_id", id));
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        userRoleService.remove(new QueryWrapper<UserRole>()
                .in("user_id", idList));
        return super.removeByIds(idList);
    }

    public boolean isExit(User user) {
        User one = getOne(new QueryWrapper<User>()
                .eq("name", user.getName()));
        return one != null && !user.getId().equals(one.getId());
    }

}
