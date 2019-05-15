package com.rainingsince.admin.permission.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainingsince.admin.permission.entity.Permission;
import com.rainingsince.admin.permission.error.PermissionError;
import com.rainingsince.admin.permission.mapper.PermisssionMapper;
import com.rainingsince.admin.rolePermission.entity.RolePermission;
import com.rainingsince.admin.rolePermission.service.RolePermissionService;
import com.rainingsince.web.response.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PermissionService extends ServiceImpl<PermisssionMapper, Permission> {

    private RolePermissionService rolePermissionService;

    @Override
    public boolean save(Permission entity) {
        entity.setId(IdWorker.getIdStr());
        return super.save(entity);
    }

    public ResponseEntity saveNotExit(Permission entity) {
        if (isExit(entity)) return ResponseBuilder.error(PermissionError.PERMISSION_EXIT);
        return ResponseBuilder.ok(save(entity));
    }

    public ResponseEntity updateNotExit(Permission entity) {
        if (isExit(entity)) return ResponseBuilder.error(PermissionError.PERMISSION_EXIT);
        return ResponseBuilder.ok(updateById(entity));
    }


    public IPage<Permission> pages(Permission entity) {
        return super.baseMapper.selectPage(entity.toPage(),
                new QueryWrapper<>(entity));
    }

    public boolean isExit(Permission entity) {
        return super.getOne(new QueryWrapper<Permission>()
                .eq("code", entity.getCode())) != null;
    }

    @Override
    public boolean removeById(Serializable id) {
        rolePermissionService.remove(new QueryWrapper<RolePermission>()
                .eq("permission_id", id));
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        rolePermissionService.remove(new QueryWrapper<RolePermission>()
                .in("permission_id", idList));
        return super.removeByIds(idList);
    }
}
