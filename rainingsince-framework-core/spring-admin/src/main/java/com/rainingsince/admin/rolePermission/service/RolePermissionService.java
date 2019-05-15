package com.rainingsince.admin.rolePermission.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainingsince.admin.permission.entity.Permission;
import com.rainingsince.admin.permission.service.PermissionService;
import com.rainingsince.admin.rolePermission.entity.RolePermission;
import com.rainingsince.admin.rolePermission.mapper.RolePermissionMapper;
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
public class RolePermissionService extends
        ServiceImpl<RolePermissionMapper, RolePermission> {

    @Override
    public boolean save(RolePermission entity) {
        entity.setId(IdWorker.getIdStr());
        return super.save(entity);
    }

    public Long saveRolePermissions(Serializable roleId, List<String> permissions) {
        if ("0".equals(roleId)) return 0L;
        return permissions.stream().map(id -> {
            RolePermission insert = new RolePermission();
            insert.setPermissionId(id);
            insert.setRoleId((String) roleId);
            return save(insert);
        }).count();
    }

    public List<String> listPermissionByRoleId(Serializable roleId) {
        return list(new QueryWrapper<RolePermission>().eq("role_id", roleId))
                .stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
    }

    public List<String> listPermissionByRoleIds(List<String> roldIds) {
        return list(new QueryWrapper<RolePermission>().in("role_id", roldIds))
                .stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
    }

}
