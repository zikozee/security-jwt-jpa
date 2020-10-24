package com.zikozee.securityjwtjpa.Domain.permission;

import com.zikozee.securityjwtjpa.Domain.permission.Permission;

public interface PermissionService {
    Permission findByName(String name);

    Permission save(Permission permission);
}
