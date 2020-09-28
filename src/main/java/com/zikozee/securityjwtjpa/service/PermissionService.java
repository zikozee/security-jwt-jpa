package com.zikozee.securityjwtjpa.service;

import com.zikozee.securityjwtjpa.Domain.Permission;

public interface PermissionService {
    Permission findByName(String name);

    Permission save(Permission permission);
}
