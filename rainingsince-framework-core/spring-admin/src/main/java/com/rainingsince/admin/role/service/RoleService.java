package com.rainingsince.admin.role.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainingsince.admin.permission.entity.Permission;
import com.rainingsince.admin.permission.service.PermissionService;
import com.rainingsince.admin.role.entity.Role;
import com.rainingsince.admin.role.error.RoleError;
import com.rainingsince.admin.role.mapper.RoleMapper;
import com.rainingsince.admin.rolePermission.entity.RolePermission;
import com.rainingsince.admin.rolePermission.service.RolePermissionService;
import com.rainingsince.admin.userRole.entity.UserRole;
import com.rainingsince.admin.userRole.service.UserRoleService;
import com.rainingsince.web.response.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    private RolePermissionService rolePermissionService;
    private PermissionService permissionService;
    private UserRoleService userRoleService;

    public IPage<Role> pages(Role role) {
        return page(role.toPage(), new QueryWrapper<>(role));
    }

    @Override
    public boolean save(Role entity) {
        entity.setId(IdWorker.getIdStr());
        return super.save(entity);
    }

    public ResponseEntity saveNotExit(Role entity) {
        if (isExit(entity)) return ResponseBuilder.error(RoleError.ROLE_EXIT);
        return ResponseBuilder.ok(save(entity));
    }

    public Long saveRolePermissions(String id, List<String> permissions) {
        return rolePermissionService.saveRolePermissions(id, permissions);
    }

    public ResponseEntity updateNotExit(Role entity) {
        if (isExit(entity)) return ResponseBuilder.error(RoleError.ROLE_EXIT);
        return ResponseBuilder.ok(updateById(entity));
    }

    public boolean isExit(Role entity) {
        return getOne(new QueryWrapper<Role>().eq("name", entity.getName())) != null;
    }

    @Override
    public boolean removeById(Serializable id) {
        rolePermissionService.remove(new QueryWrapper<RolePermission>()
                .eq("role_id", id));
        userRoleService.remove(new QueryWrapper<UserRole>()
                .eq("role_id", id));
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        rolePermissionService.remove(new QueryWrapper<RolePermission>()
                .in("role_id", idList));
        userRoleService.remove(new QueryWrapper<UserRole>()
                .in("role_id", idList));
        return super.removeByIds(idList);
    }

    @Override
    public Role getById(Serializable id) {
        List<String> permissionList = null;
        if ("0".equals(id))
            permissionList = permissionService.list()
                    .stream()
                    .map(Permission::getId)
                    .collect(Collectors.toList());
        else
            permissionList = rolePermissionService.listPermissionByRoleId(id);
        Role role = super.getById(id);
        role.setPermissionList(permissionList);
        return role;
    }


}
