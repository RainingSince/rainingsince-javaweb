package com.rainingsince.admin.shiro;

import com.rainingsince.admin.permission.entity.Permission;
import com.rainingsince.admin.permission.service.PermissionService;
import com.rainingsince.admin.role.entity.Role;
import com.rainingsince.admin.role.error.RoleError;
import com.rainingsince.admin.role.service.RoleService;
import com.rainingsince.admin.rolePermission.service.RolePermissionService;
import com.rainingsince.admin.user.service.UserService;
import com.rainingsince.admin.userRole.service.UserRoleService;
import com.rainingsince.shiro.ShiroRealm;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MyShiroReaml extends ShiroRealm {

    private PermissionService permissionService;
    private RolePermissionService rolePermissionService;
    private RoleService roleService;
    private UserRoleService userRoleService;

    @Override
    public AuthorizationInfo getCustomAuthorizationInfo(PrincipalCollection principalCollection) {
        String userId = principalCollection.getPrimaryPrincipal().toString();
        if (StringUtils.isEmpty(userId)) return null;
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roles = null;
        if ("0".equals(userId)) {
            roles = roleService.list();
        } else
            roles = (List<Role>) roleService.
                    listByIds(userRoleService.listRolesByUserId(userId));
        boolean isAdmin = roles.stream()
                .anyMatch(role -> "0".equals(role.getId()));
        Set<String> permissions;
        Set<String> roleNames = roles
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        authorizationInfo.setRoles(roleNames);
        if (isAdmin) {
            permissions = permissionService.list().stream()
                    .map(Permission::getCode)
                    .collect(Collectors.toSet());
        } else {
            permissions = rolePermissionService.listPermissionByRoleIds(roles.stream().map(Role::getId)
                    .collect(Collectors.toList()))
                    .stream().map(id -> permissionService.getById(id).getCode())
                    .collect(Collectors.toSet());
        }
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }
}
