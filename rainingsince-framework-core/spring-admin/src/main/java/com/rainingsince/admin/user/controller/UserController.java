package com.rainingsince.admin.user.controller;

import com.rainingsince.admin.user.entity.User;
import com.rainingsince.admin.user.service.UserService;
import com.rainingsince.web.response.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/list")
    @RequiresPermissions("users:list")
    public ResponseEntity getAllRoles() {
        return ResponseBuilder.ok(userService.list());
    }

    @GetMapping()
    @RequiresPermissions("users:list")
    public ResponseEntity getRolesPage(User user) {
        return ResponseBuilder.ok(userService.pages(user));
    }

    @GetMapping("/{userId}")
    @RequiresPermissions("users:info")
    public ResponseEntity getRole(@PathVariable String userId) {
        return ResponseBuilder.ok(userService.getById(userId));
    }

    @PostMapping()
    @RequiresPermissions("users:add")
    public ResponseEntity insertRole(@RequestBody User user) {
        return userService.saveNotExit(user);
    }

    @PutMapping
    @RequiresPermissions("users:modify")
    public ResponseEntity updateRole(@RequestBody User user) {
        return userService.updateNotExit(user);
    }

    @PutMapping("/{userId}/select/roles")
    @RequiresPermissions("users:roles:choose")
    public ResponseEntity updatePermissionByRole(@PathVariable String userId,
                                                 @RequestBody List<String> roles) {
        return ResponseBuilder.ok(userService.saveUserRoles(userId, roles));
    }

    @DeleteMapping("/{userId}")
    @RequiresPermissions("users:delete")
    public ResponseEntity deleteRole(@PathVariable String userId) {
        return ResponseBuilder.ok(userService.removeById(userId));
    }

    @DeleteMapping("/select")
    @RequiresPermissions("users:delete")
    public ResponseEntity deleteRoles(@RequestBody List<String> ids) {
        return ResponseBuilder.ok(userService.removeByIds(ids));
    }

}
