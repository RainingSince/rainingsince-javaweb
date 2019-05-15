package com.rainingsince.admin.role.controller;

import com.rainingsince.admin.role.entity.Role;
import com.rainingsince.admin.role.service.RoleService;
import com.rainingsince.web.response.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/role")
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;

    @GetMapping("/list")
    @RequiresPermissions("roles:list")
    public ResponseEntity getAllRoles() {
        return ResponseBuilder.ok(roleService.list());
    }

    @GetMapping()
    @RequiresPermissions("roles:list")
    public ResponseEntity getRolesPage(Role role) {
        return ResponseBuilder.ok(roleService.pages(role));
    }

    @GetMapping("/{roleId}")
    @RequiresPermissions("roles:info")
    public ResponseEntity getRole(@PathVariable String roleId) {
        return ResponseBuilder.ok(roleService.getById(roleId));
    }

    @PostMapping()
    @RequiresPermissions("roles:add")
    public ResponseEntity insertRole(@RequestBody Role role) {
        return roleService.saveNotExit(role);
    }

    @PutMapping
    @RequiresPermissions("roles:modify")
    public ResponseEntity updateRole(@RequestBody Role role) {
        return roleService.updateNotExit(role);
    }

    @PutMapping("/{roleId}/select/permissions")
    @RequiresPermissions("roles:permissions:choose")
    public ResponseEntity updatePermissionByRole(@PathVariable String roleId,
                                                 @RequestBody List<String> perms) {
        return ResponseBuilder.ok(roleService.saveRolePermissions(roleId, perms));
    }

    @DeleteMapping("/{roleId}")
    @RequiresPermissions("roles:delete")
    public ResponseEntity deleteRole(@PathVariable String roleId) {
        return ResponseBuilder.ok(roleService.removeById(roleId));
    }

    @DeleteMapping("/select")
    @RequiresPermissions("roles:delete")
    public ResponseEntity deleteRoles(@RequestBody List<String> ids) {
        return ResponseBuilder.ok(roleService.removeByIds(ids));
    }

}
