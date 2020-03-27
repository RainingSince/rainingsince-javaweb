package com.rainingsince.admin.permission.controller;

import com.rainingsince.admin.permission.entity.Permission;
import com.rainingsince.admin.permission.service.PermissionService;
import com.rainingsince.web.response.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/permission")
@AllArgsConstructor
public class PermissionController {

    private PermissionService permissionService;

    @GetMapping("/list")
    @RequiresPermissions("permissions:list")
    public ResponseEntity getAllPermission() {
        return ResponseBuilder.success(permissionService.list());
    }

    @GetMapping
    @RequiresPermissions("permissions:list")
    public ResponseEntity getPagePermission(Permission permission) {
        return ResponseBuilder.success(permissionService.pages(permission));
    }

    @GetMapping("/{id}")
    @RequiresPermissions("permissions:info")
    public ResponseEntity getPermission(@PathVariable String id) {
        return ResponseBuilder.success(permissionService.getById(id));
    }

    @PostMapping
    @RequiresPermissions("permissions:add")
    public ResponseEntity savePermission(@RequestBody Permission permission) {
        return permissionService.saveNotExit(permission);
    }

    @PutMapping
    @RequiresPermissions("permissions:modify")
    public ResponseEntity updatePermission(@RequestBody Permission permission) {
        return permissionService.updateNotExit(permission);
    }

    @DeleteMapping("/{id}")
    @RequiresPermissions("permissions:delete")
    public ResponseEntity deletePermission(@PathVariable String id) {
        return ResponseBuilder.success(permissionService.removeById(id));
    }

    @DeleteMapping()
    @RequiresPermissions("permissions:delete")
    public ResponseEntity deletePermissions(@RequestBody List<String> ids) {
        return ResponseBuilder.success(permissionService.removeByIds(ids));
    }

}
